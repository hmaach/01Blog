import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private TOKEN_KEY = 'accessToken';
  private TOKEN_EXPIRES_KEY = 'tokenExpiresAt';
  private USER_KEY = 'user';

  private isBrowser: boolean;

  constructor() {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  saveTokens(token: string, expiresAt: string): void {
    if (this.isBrowser && token) {
      localStorage.setItem(this.TOKEN_KEY, token);
      localStorage.setItem(this.TOKEN_EXPIRES_KEY, expiresAt);
    }
  }

  getAccessToken(): string | null {
    return this.isBrowser ? localStorage.getItem(this.TOKEN_KEY) : null;
  }

  getUser(): User | null {
    if (this.isBrowser) {
      const data = localStorage.getItem(this.USER_KEY);
      return data ? JSON.parse(data) : null;
    }
    return null;
  }

  clear(): void {
    if (this.isBrowser) {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
      localStorage.removeItem(this.TOKEN_EXPIRES_KEY);
    }
  }
}
