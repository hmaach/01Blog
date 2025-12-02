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
  //TODO: add pagination to comments
  fetchComments(
    postId: string,
    lastCommentTime: string | null,
    limit: number
  ): Observable<Comment[]> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Comment[]>(`${this.apiUrl}/comments/${postId}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchComment(commentId: string): Observable<Comment> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Comment>(`${this.apiUrl}/comments/details/${commentId}`, {
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

  deleteComment(commentId: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.delete<void>(`${this.apiUrl}/comments/${commentId}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }
}
