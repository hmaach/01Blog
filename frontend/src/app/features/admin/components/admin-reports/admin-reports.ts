import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { mockReports } from '../../../../shared/lib/mock-data';

@Component({
  selector: 'app-admin-reports',
  imports: [CommonModule, MatCardModule],
  templateUrl: './admin-reports.html',
  styleUrl: './admin-reports.scss',
})
export class AdminReports {
  reports = [...mockReports];

  handleUpdateReportStatus(reportId: string, status: 'waiting' | 'approved' | 'declined') {
    this.reports = this.reports.map((r) => (r.id === reportId ? { ...r, status } : r));
    console.log('Update report status:', reportId, status);
  }
}
