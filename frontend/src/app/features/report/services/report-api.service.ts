import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ReportUserPayload } from '../models/report-user.model';
import { ReportPostPayload } from '../models/report-post.model';
import { ReportCommentPayload } from '../models/report-comment.model';
import { StorageService } from '../../../core/services/storage.service';
import { ToastService } from '../../../core/services/toast.service';

@Injectable({ providedIn: 'root' })
export class ReportApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private tokenService = inject(StorageService);
  private toast = inject(ToastService);

  constructor(private http: HttpClient) { }

  reportUser(payload: ReportUserPayload): Observable<void> {
    const token = this.tokenService.getAccessToken();

    return this.http.post<void>(`${this.apiUrl}/report/user/${payload.userId}`, payload, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }

  reportPost(payload: ReportPostPayload): Observable<void> {
    const token = this.tokenService.getAccessToken();

    return this.http.post<void>(`${this.apiUrl}/report/post/${payload.postId}`, payload, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }

  reportComment(payload: ReportCommentPayload): Observable<void> {
    const token = this.tokenService.getAccessToken();

    return this.http.post<void>(`${this.apiUrl}/report/comment/${payload.commentId}`, payload, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    })
      .pipe(
        tap({
          error: (e) => this.toast.show(e?.error?.message || 'Unknown Server Error', 'error'),
        })
      );
  }
}
