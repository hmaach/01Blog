import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../../../core/services/storage.service';
import { Report } from '../models/report-model';

@Injectable({ providedIn: 'root' })
export class AdminApiService {
  private readonly apiUrl = `${environment.apiUrl}`;
  private storageService = inject(StorageService);

  constructor(private http: HttpClient) {}

  fetchReports(lastPostTime: string | null, limit: number): Observable<Report[]> {
    const token = this.storageService.getAccessToken();
    const params = new URLSearchParams();

    params.append('size', limit.toString());
    if (lastPostTime) params.append('before', lastPostTime);

    return this.http.get<Report[]>(`${this.apiUrl}/reports`, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${token}`),
    });
  }
}
