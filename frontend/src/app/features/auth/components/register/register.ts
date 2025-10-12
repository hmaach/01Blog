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

  private _fb = inject(FormBuilder);

  firstFormGroup = this._fb.group({
    firstCtrl: ['', Validators.required],
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

  avatarPreview: string | null = null;

  onAvatarSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.avatarPreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  removeAvatar(): void {
    this.avatarPreview = null;
  }

  finishRegistration(): void {
    // Here you can handle the final submission (including avatar)
    console.log('Form data:', {
      ...this.firstFormGroup.value,
      ...this.secondFormGroup.value,
      avatar: this.avatarPreview ? 'uploaded' : 'none',
    });
  }
}
