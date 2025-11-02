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

  fetchFeedPosts(lastPostTime: string | null, limit: number): Observable<Post[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastPostTime) params.append('before', lastPostTime);

    return this.http.get<Post[]>(`${this.apiUrl}/posts/feed?${params.toString()}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchExplorePosts(lastPostTime: string | null, limit: number): Observable<Post[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastPostTime) params.append('before', lastPostTime);

    return this.http.get<Post[]>(`${this.apiUrl}/posts/explore?${params.toString()}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchUserPosts(username: string, lastPostTime: string | null, limit: number): Observable<Post[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastPostTime) params.append('before', lastPostTime);

    return this.http.get<Post[]>(`${this.apiUrl}/posts/user/${username}?${params.toString()}`, {
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
