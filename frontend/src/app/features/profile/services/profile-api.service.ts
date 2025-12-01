import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { UserResponse } from '../models/user-response.model';
import { StorageService } from '../../../core/services/storage.service';

@Injectable({ providedIn: 'root' })
export class ProfileApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);

  constructor(private http: HttpClient) {}

  fetchUsers(
    q: string | null,
    lastUserTime: string | null,
    limit: number
  ): Observable<UserResponse[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    if (q) params.append('query', q);
    params.append('size', limit.toString());
    if (lastUserTime) params.append('before', lastUserTime);

    return this.http.get<UserResponse[]>(`${this.apiUrl}/user/all?${params.toString()}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchUserProfile(username: string): Observable<UserResponse> {
    const token = this.storageService.getAccessToken();

    return this.http.get<UserResponse>(`${this.apiUrl}/user/${username}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchUserReadme(userId: string): Observable<string> {
    const token = this.storageService.getAccessToken();

    return this.http.get(`${this.apiUrl}/user/${userId}/readme`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      responseType: 'text',
    });
  }

  subscribe(targetUserId: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.post<void>(
      `${this.apiUrl}/user/subscribe/${targetUserId}`,
      {},
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      }
    );
  }

  unsubscribe(targetUserId: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.delete<void>(`${this.apiUrl}/user/subscribe/${targetUserId}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }
}
