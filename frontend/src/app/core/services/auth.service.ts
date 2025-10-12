import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap, of } from 'rxjs';
import { AuthApiService } from '../../features/auth/services/auth-api.service';
import { TokenService } from './token.service';
import { ToastService } from './toast.service';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApi = inject(AuthApiService);
  private tokenService = inject(TokenService);
  private router = inject(Router);

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private toast: ToastService) {
    const user = this.tokenService.getUser();
    if (user) this.currentUserSubject.next(user);
  }

  login(email: string, password: string): Observable<string> {
    return this.authApi.login({ email, password }).pipe(
      tap({
        next: (token) => {
          this.tokenService.saveTokens(token);
          //   this.currentUserSubject.next(user);
          this.toast.show('Welcome back', 'success');
          this.router.navigate(['/']);
        },
        error: () => this.toast.show('Invalid credentials', 'error'),
      })
    );
  }

  isAuthenticated(): Observable<boolean> {
    const token = this.tokenService.getAccessToken();

    if (!token) {
      return of(false);
    }

    return this.authApi.isAuthenticated(token);
  }

  register(data: { name: string; email: string; password: string }): Observable<User> {
    return this.authApi.register(data).pipe(
      tap({
        next: (user) => {
          this.tokenService.saveTokens(user.token);
          this.currentUserSubject.next(user);
          this.toast.show('Account created successfully', 'success');
          this.router.navigate(['/']);
        },
        error: () => this.toast.show('Registration failed', 'error'),
      })
    );
  }

  logout(): void {
    this.tokenService.clear();
    this.currentUserSubject.next(null);
    this.toast.show('Logged out', 'info');
    this.router.navigate(['/auth/login']);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }
}
