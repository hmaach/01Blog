import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap, of, catchError } from 'rxjs';
import { AuthApiService } from '../../features/auth/services/auth-api.service';
import { TokenService } from './token.service';
import { ToastService } from './toast.service';
import { User } from '../models/user.model';
import { LoginResponse } from '../models/login-response.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApi = inject(AuthApiService);
  private tokenService = inject(TokenService);
  private router = inject(Router);

  private isBrowser: boolean;

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private toast: ToastService) {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
    if (this.isBrowser) {
      window.addEventListener('storage', this.handleTokenChange.bind(this));
    }
    // const user = this.tokenService.getUser();
    // if (user) this.currentUserSubject.next(user);
  }

  login(email: string, password: string): Observable<LoginResponse> {
    return this.authApi.login({ email, password }).pipe(
      tap({
        next: (response) => {
          this.tokenService.saveTokens(response.token, response.expiresAt);
          this.toast.show('Welcome back', 'success');
          this.router.navigate(['/']);
        },
        error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
      })
    );
  }

  register(data: FormData): Observable<User> {
    return this.authApi.register(data).pipe(
      tap({
        next: (user) => {
          // TODO: pass the email to the login page
          // this.tokenService.saveTokens(user.token);
          // this.currentUserSubject.next(user);
          this.toast.show('Account created successfully', 'success');
          this.router.navigate(['/']);
        },
        error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
      })
    );
  }

  isAuthenticated(): Observable<boolean> {
    if (!this.isBrowser) {
      return of(true);
    }

    const token = this.tokenService.getAccessToken();
    if (!token) {
      return of(false);
    }

    return of(true);
  }

  private handleTokenChange(event: StorageEvent): void {
    this.validateToken();
  }

  validateToken() {
    if (!this.isBrowser) return;

    const token = this.tokenService.getAccessToken();
    if (!token) return;

    this.authApi
      .isAuthenticated(token)
      .pipe(
        tap((isValid) => {
          if (!isValid) {
            this.tokenService.clear();
            this.router.navigate(['/auth/login']);
          }
        }),
        catchError(() => {
          this.tokenService.clear();
          this.router.navigate(['/auth/login']);
          return of(false);
        })
      )
      .subscribe();
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
