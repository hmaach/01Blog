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
import { AuthService } from '../../../../core/services/auth.service';

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
    nameCtrl: ['', Validators.required],
    emailCtrl: ['', [Validators.required, Validators.email]],
  });

  secondFormGroup = this._fb.group({
    passwordCtrl: ['', Validators.required],
    confirmCtrl: ['', Validators.required],
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
    const formData: RegisterData = {
      name: this.firstFormGroup.value.nameCtrl || '',
      email: this.firstFormGroup.value.emailCtrl || '',
      password: this.secondFormGroup.value.passwordCtrl || '',
      avatar: this.avatarPreview || null,
    };

    // console.log('Form data:', formData);
    this.authService.register(formData).subscribe();
  }
}
