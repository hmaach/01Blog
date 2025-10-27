import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap, of, catchError, switchMap } from 'rxjs';
import { AuthApiService } from './auth-api.service';
import { StorageService } from '../../../core/services/storage.service';
import { ToastService } from '../../../core/services/toast.service';
import { CurrentUserInfo, User } from '../../../core/models/user.model';
import { LoginResponse } from '../../../core/models/login-response.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApi = inject(AuthApiService);
  private storageService = inject(StorageService);
  private router = inject(Router);

  private isBrowser: boolean;

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private toast: ToastService) {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
    if (this.isBrowser) {
      window.addEventListener('storage', this.handleTokenChange.bind(this));
    }
  }

  private handleTokenChange(event: StorageEvent): void {
    this.validateToken();

    const token = this.storageService.getAccessToken();
    if (token) {
      this.authApi.getCurrentUser(token).subscribe({
        next: (user: CurrentUserInfo) => {
          this.storageService.saveUser(user);
        },
        error: () => {
          this.storageService.clear();
          this.router.navigate(['/auth/login']);
        },
      });
    }
  }

  login(email: string, password: string): Observable<CurrentUserInfo> {
    return this.authApi.login({ email, password }).pipe(
      switchMap((response) => {
        this.storageService.saveTokens(response.token, response.expiresAt);
        return this.authApi.getCurrentUser(response.token);
      }),
      tap({
        next: (user) => {
          this.storageService.saveUser(user);
          this.toast.show(`Welcome back, ${user.username}!`, 'success');
          this.router.navigate(['/']);
        },
        error: (e) => {
          this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        },
      })
    );
  }

  register(data: FormData): Observable<User> {
    return this.authApi.register(data).pipe(
      tap({
        next: () => {
          this.toast.show('Account created successfully', 'success');
          this.router.navigateByUrl('/auth/login', { state: { email: data.get('email') } });
        },
        error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
      })
    );
  }

  isAuthenticated(): Observable<boolean> {
    if (!this.isBrowser) {
      return of(true);
    }

    const token = this.storageService.getAccessToken();
    if (!token) {
      return of(false);
    }

    return of(true);
  }

  validateToken() {
    if (!this.isBrowser) return;

    const token = this.storageService.getAccessToken();
    if (!token) return;

    this.authApi
      .isAuthenticated(token)
      .pipe(
        tap((isValid) => {
          if (!isValid) {
            this.storageService.clear();
            this.router.navigate(['/auth/login']);
          }
        }),
        catchError(() => {
          this.storageService.clear();
          this.router.navigate(['/auth/login']);
          return of(false);
        })
      )
      .subscribe();
  }

  logout(): void {
    this.storageService.clear();
    this.toast.show('Logged out', 'info');
    this.router.navigate(['/auth/login']);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }
}
