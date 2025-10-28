import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ToastService } from '../../../../core/services/toast.service';
import { AuthService } from '../../services/auth.service';
import { trimValidator } from '../../../../shared/lib/validators';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.scss'],
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private toast = inject(ToastService);
  private isBrowser: boolean;

  hide: boolean = true;
  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, trimValidator]],
  });

  constructor() {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      const stateData = history.state;

      if (stateData && stateData.email) {
        this.form.get('email')?.setValue(stateData.email);
      }
    }
  }

  onSubmit() {
    if (this.form.invalid) {
      this.toast.show('Please fill out all fields correctly.', 'error');
      return;
    }

    const { email, password } = this.form.value;
    this.authService.login(email?.trim()!, password?.trim()!).subscribe();
  }
}
