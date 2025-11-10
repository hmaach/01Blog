import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { StorageService } from './storage.service';
import { ToastService } from './toast.service';
import { ReportPayload } from '../../shared/models/report.model';

@Injectable({ providedIn: 'root' })
export class ReportApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) {}

  reportPost(payload: ReportPayload): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http
      .post<void>(`${this.apiUrl}/reports`, payload, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }
}
