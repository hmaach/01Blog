import { Injectable, inject } from '@angular/core';
import { StorageService } from './storage.service';
import { environment } from '../../../environments/environment';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastService } from './toast.service';

@Injectable({
  providedIn: 'root',
})
export class BlobService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) {}

  loadBlob(avatarPath: string): Observable<string> {
    return this.fetch(avatarPath).pipe(
      map((blob) => URL.createObjectURL(blob)),
      catchError((err) => {
        console.error('Failed to fetch avatar blob:', err);
        this.toast.show('Failed to fetch avatar', 'error');
        return throwError(() => err);
      })
    );
  }

  private fetch(url: string): Observable<Blob> {
    const token = this.storageService.getAccessToken();
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
