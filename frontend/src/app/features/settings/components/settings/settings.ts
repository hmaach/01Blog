import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { SettingsService } from '../../services/settings.service';
import { ToastService } from '../../../../core/services/toast.service';
import { StorageService } from '../../../../core/services/storage.service';
import { BlobService } from '../../../../core/services/blob.service';
import { CurrentUserResponse } from '../../../profile/models/user-response.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { trimValidator } from '../../../../shared/lib/validators';
import { Confirmation } from '../../../../shared/components/confirmation/confirmation';
import { MarkdownModule } from 'ngx-markdown';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatTabsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatProgressBarModule,
    Spinner,
    MarkdownModule,
    FormsModule,
  ],
  templateUrl: './settings.html',
  styleUrls: ['./settings.scss'],
})
export class Settings implements OnInit {
  private fb = inject(FormBuilder);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);
  private blobService = inject(BlobService);
  private settingsService = inject(SettingsService);
  private storageService = inject(StorageService);

  maxNameChars: number = 100;
  maxEmailChars: number = 100;
  maxPasswordChars: number = 100;

  userInfo!: CurrentUserResponse;

  isLoading = true;
  isFetching = true;
  avatar: string | null = null;
  readmePreview = '';
  readmeFile: File | null = null;

  activeTab = 'profile';
  activeTabIndex = 0;

  infoForm = this.fb.group({
    name: [
      {
        value: '',
        disabled: this.isLoading,
      },
      [Validators.required, trimValidator, Validators.maxLength(100)],
    ],
    email: [
      {
        value: '',
        disabled: this.isLoading || this.userInfo.emailVerified,
      },
      [Validators.required, trimValidator, Validators.email, Validators.maxLength(100)],
    ],
  });

  readmeForm = this.fb.group({
    readme: [
      {
        value: '',
        disabled: this.isLoading,
      },
      Validators.required,
    ],
  });

  passwordForm = this.fb.group({
    currentPassword: [
      {
        value: '',
        disabled: this.isLoading,
      },
      ,
      [Validators.required, trimValidator],
    ],
    newPassword: [
      {
        value: '',
        disabled: this.isLoading,
      },
      ,
      [Validators.required, trimValidator, Validators.minLength(6)],
    ],
    confirmPassword: [
      {
        value: '',
        disabled: this.isLoading,
      },
      ,
      [Validators.required, trimValidator],
    ],
  });

  constructor() {}

  ngOnInit(): void {
    this.loadUserInfo();
  }

  private loadUserInfo() {
    const currUserId = this.storageService.getCurrentUserInfo()?.id;
    if (!currUserId) return;

    this.settingsService.fetchUserInfo(currUserId).subscribe({
      next: (res: CurrentUserResponse) => {
        this.userInfo = res;

        this.infoForm.patchValue({
          name: res.name,
          email: res.email,
        });

        if (res.avatarUrl) {
          this.blobService.loadBlob(res.avatarUrl).subscribe((url) => {
            this.userInfo.avatarUrl = url;
          });
        }

        // Mark loading finished
        this.isLoading = false;
        this.isFetching = false;

        // Enable entire form group
        this.infoForm?.enable();
        this.readmeForm?.enable();
        // this.passwordForm.enable();

        // // Re-disable fields based on logic
        if (this.userInfo.emailVerified) {
          this.infoForm.get('email')?.disable();
        }

        this.infoForm.get('username')?.disable();
      },

      error: (e: HttpErrorResponse) => {
        this.toast.show(e?.error?.message || 'Unknown server error', 'error');
        this.isLoading = false;
        this.isFetching = false;
      },
    });
  }

  onTabChange(index: number) {
    this.activeTabIndex = index;
    this.activeTab = ['profile', 'readme', 'password', 'account'][index];
  }

  handleAvatarChange(event: any) {
    const file = event.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onloadend = () => (this.avatar = reader.result as string);
    reader.readAsDataURL(file);
  }

  handleReadmeUpload(event: any) {
    const file = event.target.files?.[0];
    if (!file || !file.name.endsWith('.md')) return;

    this.readmeFile = file;

    const reader = new FileReader();
    reader.onloadend = () => {
      this.readmePreview = reader.result as string;
      this.readmeForm.patchValue({ readme: this.readmePreview });
    };
    reader.readAsText(file);
  }

  handleSaveProfile() {
    console.log(this.isInfoFormValid());

    if (this.infoForm.invalid) return;

    this.isLoading = true;
    const formData = new FormData();

    if (this.userInfo.name !== this.infoForm.value.name!.trim())
      formData.append('name', this.infoForm.value.name!.trim());

    if (this.userInfo.email !== this.infoForm.value.email!.trim())
      formData.append('email', this.infoForm.value.email!.trim());

    if (this.avatar) {
      const byteCharacters = atob(this.avatar.split(',')[1]);
      const byteArray = new Uint8Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteArray[i] = byteCharacters.charCodeAt(i);
      }
      formData.append('avatar', new Blob([byteArray], { type: 'image/png' }), 'avatar.png');
    }

    for (const [key, value] of formData.entries()) {
      console.log(`${key}: ${value}`);
    }
    this.isLoading = false;

    this.settingsService.updateUserInfo(formData).subscribe({
      next: () => {
        this.toast.show('Profile updated successfully!', 'success');
        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown server error', 'error');
        this.isLoading = false;
      },
    });
  }

  isInfoFormValid(): boolean {
    return (
      (this.userInfo.name !== this.infoForm.value.name!.trim() ||
        this.userInfo.email !== this.infoForm.value.email!.trim() ||
        this.avatar !== null) &&
      this.isLoading === false
    );
  }

  handleSaveReadme() {
    if (this.readmeForm.invalid) return;

    this.isLoading = true;
    const formData = new FormData();
    formData.append('content', this.readmeForm.value.readme!);

    this.settingsService.updateReadme(formData).subscribe({
      next: () => {
        this.toast.show('README updated successfully!', 'success');
        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown server error', 'error');
        this.isLoading = false;
      },
    });
  }

  handleChangePassword() {
    if (this.passwordForm.invalid) return;

    const { newPassword, confirmPassword } = this.passwordForm.value;
    if (newPassword !== confirmPassword) {
      this.toast.show('Passwords do not match', 'success');
      return;
    }

    this.isLoading = true;
    const formData = new FormData();

    formData.append('currentPassword', this.passwordForm.value.currentPassword!);
    formData.append('newPassword', newPassword!);
    formData.append('confirmPassword', confirmPassword!);

    this.settingsService.updatePassword(formData).subscribe({
      next: () => {
        this.toast.show('Password updated!', 'success');
        this.passwordForm.reset();
        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown server error', 'error');
        this.isLoading = false;
      },
    });
  }

  handleDeleteAccount() {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: 'Are you sure you want to delete your account ?' },
      panelClass: 'delete-account',
    });
    dialogRef?.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.isFetching = true;
        this.settingsService.deleteAccount().subscribe();
      }
    });
  }
}
