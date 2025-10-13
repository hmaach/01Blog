import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { User } from '../../../core/models/user.model';
import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private readonly apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {}

  login(payload: { email: string; password: string }): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/auth/login`, payload, {
      responseType: 'text' as 'json',
    });
  }

  isAuthenticated(token: string): Observable<boolean> {
    return this.http
      .get(`${this.apiUrl}/validate-token`, {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      })
      .pipe(
        tap(() => console.log('Token valid')),
        map(() => true),
        catchError((err) => {
          console.error('Token invalid:', err);
          return of(false);
        })
      );
  }

  register(payload: { name: string; email: string; password: string }): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/auth/register`, payload);
  }
}
