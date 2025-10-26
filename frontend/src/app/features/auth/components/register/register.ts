import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatStepperModule } from '@angular/material/stepper';
import { ToastService } from '../../../../core/services/toast.service';
import { AuthService } from '../../services/auth.service';
import { trimValidator } from '../../../../shared/lib/validators';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatStepperModule,
  ],
  templateUrl: './register.html',
  styleUrls: ['./register.scss'],
})
export class Register {
  pwdHide = true;
  ConfirmPwdHide = true;
  isLinear = true;
  avatarPreview: string | null = null;

  private toast = inject(ToastService);
  private _fb = inject(FormBuilder);
  private authService = inject(AuthService);

  firstFormGroup = this._fb.group({
    nameCtrl: ['', [Validators.required, trimValidator]],
    emailCtrl: [
      '',
      [
        Validators.required,
        Validators.email,
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/),
      ],
    ],
  });

  secondFormGroup = this._fb.group({
    passwordCtrl: ['', [Validators.minLength(6), trimValidator]],
    confirmCtrl: ['', [Validators.required, trimValidator]],
  });

  get passwordsDoNotMatch(): boolean {
    const pwd = this.secondFormGroup.get('passwordCtrl')?.value;
    const confirm = this.secondFormGroup.get('confirmCtrl')?.value;

    return pwd !== confirm && pwd !== confirm;
  }

  onAvatarSelected(event: Event, fileInput: HTMLInputElement): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;

    const validTypes = ['image/jpeg', 'image/png', 'image/jpg'];
    if (!validTypes.includes(file.type)) {
      this.toast.show('Invalid file type. Please upload a JPG, jpeg, or PNG image', 'warning');
      fileInput.value = '';
      return;
    }

    const maxSizeMB = 2;
    if (file.size > maxSizeMB * 1024 * 1024) {
      this.toast.show(`File too large. Max size is ${maxSizeMB} MB.`, 'warning');
      fileInput.value = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      this.avatarPreview = reader.result as string;
    };
    reader.readAsDataURL(file);
  }

  removeAvatar(fileInput: HTMLInputElement): void {
    if (!fileInput) return;
    this.avatarPreview = null;
    fileInput.value = '';
  }

  finishRegistration(): void {
    const formData = new FormData();
    formData.append('name', this.firstFormGroup.value.nameCtrl?.trim() || '');
    formData.append('email', this.firstFormGroup.value.emailCtrl?.trim() || '');
    formData.append('password', this.secondFormGroup.value.passwordCtrl?.trim() || '');

    if (this.avatarPreview) {
      const byteCharacters = atob(this.avatarPreview.split(',')[1]); // remove base64 header
      const byteArray = new Uint8Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteArray[i] = byteCharacters.charCodeAt(i);
      }
      const blob = new Blob([byteArray], { type: 'image/png' }); // adjust the type if necessary
      formData.append('avatar', blob, 'avatar.png');
    }

    this.authService.register(formData).subscribe();
  }
}
