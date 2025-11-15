import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { StorageService } from '../../../core/services/storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Comment } from '../models/comment-model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CommentApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);

  constructor(private http: HttpClient) {}

  fetchComments(postId: string, lastPostTime: string | null, limit: number): Observable<Comment[]> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Comment[]>(`${this.apiUrl}/comments/${postId}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  createComment(postId: string, comment: string): Observable<Comment> {
    const token = this.storageService.getAccessToken();

    return this.http.post<Comment>(
      `${this.apiUrl}/comments/${postId}`,
      { comment },
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      }
    );
  }

  deleteComment(commentId: string) {
    const token = this.storageService.getAccessToken();

    return this.http.delete(`${this.apiUrl}/comments/${commentId}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }
}
