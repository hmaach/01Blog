import { Injectable } from '@angular/core';
import { CurrentUserInfo, User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class StorageService {
  private TOKEN_KEY = 'accessToken';
  private TOKEN_EXPIRES_KEY = 'tokenExpiresAt';
  private USER_KEY = 'user';
  private AVATAR_KEY = 'avatarUrl';
  private ROLE_KEY = 'role';

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

  getUserAvatarUrl(): string | null {
    if (this.isBrowser) {
      const role = localStorage.getItem(this.AVATAR_KEY);
      return role || null;
    }
    return null;
  }

  getUserRole(): string | null {
    if (this.isBrowser) {
      const role = localStorage.getItem(this.ROLE_KEY);
      return role || null;
    }
    return null;
  }

  saveUser(user: CurrentUserInfo) {
    const userData = {
      id: user.id,
      name: user.name,
      username: user.username,
    };

    localStorage.setItem(this.USER_KEY, JSON.stringify(userData));
    localStorage.setItem(this.AVATAR_KEY, user.avatarUrl ?? '');
    localStorage.setItem(this.ROLE_KEY, user.role ?? '');
  }

  clear(): void {
    if (this.isBrowser) {
      localStorage.removeItem(this.TOKEN_KEY);
      localStorage.removeItem(this.USER_KEY);
      localStorage.removeItem(this.AVATAR_KEY);
      localStorage.removeItem(this.ROLE_KEY);
      localStorage.removeItem(this.TOKEN_EXPIRES_KEY);
    }
  }
}
