import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserResponse } from '../models/user-response.model';
import { ToastService } from '../../../core/services/toast.service';
import { Router } from '@angular/router';
import { TokenService } from '../../../core/services/token.service'; // adjust path if needed

@Injectable({ providedIn: 'root' })
export class ProfileApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private toast = inject(ToastService);
  private tokenService = inject(TokenService);

  constructor(private http: HttpClient) {}

  fetchUserProfile(username: string): Observable<UserResponse> {
    const token = this.tokenService.getAccessToken();

    return this.http
      .get<UserResponse>(`${this.apiUrl}/user/${username}`, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }

  fetchUserReadme(userId: string): Observable<string> {
    const token = this.tokenService.getAccessToken();

    return this.http
      .get<string>(`${this.apiUrl}/user/${userId}/readme`, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }
}
