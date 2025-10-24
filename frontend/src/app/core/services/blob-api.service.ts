import { Injectable, inject } from '@angular/core';
import { StorageService } from './storage.service';
import { environment } from '../../../environments/environment';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastService } from './toast.service';

@Injectable({
  providedIn: 'root',
})
export class BlobApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private tokenService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) {}

  fetch(url: string): Observable<Blob> {
    const token = this.tokenService.getAccessToken();
    return this.http
      .get(`${this.apiUrl}/media/${url}`, {
        responseType: 'blob',
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }
}
