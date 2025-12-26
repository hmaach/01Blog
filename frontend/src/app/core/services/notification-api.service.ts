import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StorageService } from './storage.service';
import { ToastService } from './toast.service';
import { Notification } from '../models/notification.model';

@Injectable({ providedIn: 'root' })
export class NotificationApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) {}

  fetch(lastNotifTime: string | null, limit: number): Observable<Notification[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastNotifTime) params.append('before', lastNotifTime);

    return this.http.get<Notification[]>(`${this.apiUrl}/user/notifications?${params.toString()}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  markSeen(notifId: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.patch<void>(
      `${this.apiUrl}/user/notifications/${notifId}`,
      {},
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      }
    );
  }
}
