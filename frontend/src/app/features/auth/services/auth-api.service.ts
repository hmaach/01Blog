import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../core/models/user.model';
import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(payload: { email: string; password: string }): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/login`, payload, {
      responseType: 'text' as 'json',
    });
  }

  isAuthenticated(token: string): Observable<boolean> {
    return this.http.get<boolean>('/api/validate-token', {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  register(payload: { name: string; email: string; password: string }): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/register`, payload);
  }
}
