import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../../../core/services/storage.service';
import { Post } from '../models/post-model';

@Injectable({ providedIn: 'root' })
export class PostApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);

  constructor(private http: HttpClient) {}

  fetchFeedPosts(): Observable<Post[]> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Post[]>(`${this.apiUrl}/posts/feed`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchExplorePosts(): Observable<Post[]> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Post[]>(`${this.apiUrl}/posts/explore`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchUserPosts(username: string): Observable<Post[]> {
    const token = this.storageService.getAccessToken();

    return this.http.get<Post[]>(`${this.apiUrl}/posts/user/${username}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  createPost(post: FormData): Observable<Post> {
    const token = this.storageService.getAccessToken();

    return this.http.post<Post>(`${this.apiUrl}/posts`, post, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  likePost(postId: string) {
    const token = this.storageService.getAccessToken();

    return this.http.post(
      `${this.apiUrl}/posts/like/${postId}`,
      {},
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      }
    );
  }
}
