import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../../../core/services/storage.service';
import { Report } from '../models/report-model';
import { AdminStats } from '../models/stats-model';
import { UserResponse } from '../../profile/models/user-response.model';

@Injectable({ providedIn: 'root' })
export class AdminApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);

  constructor(private http: HttpClient) {}

  fetchStats(): Observable<AdminStats> {
    const token = this.storageService.getAccessToken();

    return this.http.get<AdminStats>(`${this.apiUrl}/admin/stats`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchUsers(
    q: string | null,
    lastUserTime: string | null,
    limit: number
  ): Observable<UserResponse[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    if (q) params.append('query', q);
    params.append('size', limit.toString());
    if (lastUserTime) params.append('before', lastUserTime);

    return this.http.get<UserResponse[]>(`${this.apiUrl}/admin/users?${params.toString()}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  fetchReports(lastPostTime: string | null, limit: number): Observable<Report[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastPostTime) params.append('before', lastPostTime);

    return this.http.get<Report[]>(`${this.apiUrl}/admin/reports`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }

  changeReportStatus(id: string, status: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.patch<void>(
      `${this.apiUrl}/admin/reports/${id}`,
      { status },
      {
        headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
      }
    );
  }

  deleteReport(id: string): Observable<void> {
    const token = this.storageService.getAccessToken();

    return this.http.delete<void>(`${this.apiUrl}/admin/reports/${id}`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }
}
