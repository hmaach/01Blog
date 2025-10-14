import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { User } from '../../../core/models/user.model';
import { environment } from '../../../../environments/environment';
import { LoginResponse } from '../../../core/models/login-response.model';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private readonly apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {}

  login(payload: { email: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/auth/login`, payload);
  }

  register(payload: FormData): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/auth/register`, payload);
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
}
