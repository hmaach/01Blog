import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { StorageService } from '../../../core/services/storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CurrentUserResponse } from '../../profile/models/user-response.model';

@Injectable({ providedIn: 'root' })
export class SettingsService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);
  private http = inject(HttpClient);

  fetchUserInfo(): Observable<CurrentUserResponse> {
    const token = this.storageService.getAccessToken();

    return this.http.get<CurrentUserResponse>(`${this.apiUrl}/user`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  updateUserInfo(data: FormData): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.patch<void>(`${this.apiUrl}/user`, data, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  updateReadme(data: FormData) {
    const token = this.storageService.getAccessToken();

    return this.http.patch<void>(`${this.apiUrl}/user/readme`, data, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  updatePassword(data: FormData): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.patch<void>(`${this.apiUrl}/user/password`, data, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  deleteAccount(): Observable<void> {
    throw new Error('Method not implemented.');
  }
}
