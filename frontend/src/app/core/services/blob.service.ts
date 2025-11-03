import { Injectable, inject } from '@angular/core';
import { StorageService } from './storage.service';
import { environment } from '../../../environments/environment';
import { catchError, map, Observable, tap, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ToastService } from './toast.service';
import { UploadedMedia } from '../../features/posts/models/media-model';

@Injectable({
  providedIn: 'root',
})
export class BlobService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) {}

  loadBlob(url: string): Observable<string> {
    return this.fetch(url).pipe(
      map((blob) => URL.createObjectURL(blob)),
      catchError((err) => {
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

  uploadPostMedia(payload: FormData): Observable<UploadedMedia> {
    const token = this.storageService.getAccessToken();
    return this.http
      .post<UploadedMedia>(`${this.apiUrl}/media/posts`, payload, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }
}
