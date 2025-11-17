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
  // reports = [...mockReports];
  reports = [
    {
      id: '1',
      reportedUser: 'Spam Account',
      reportedUsername: 'spammer123',
      reporter: 'Sarah Chen',
      reason: 'Multiple spam posts and harassment',
      createdAt: '2025-10-15T09:30:00Z',
      status: 'pending',
    },
    {
      id: '2',
      reportedUser: 'Rude User',
      reportedUsername: 'rudeuser',
      reporter: 'Mike Johnson',
      reason: 'Offensive language and harassment in comments',
      createdAt: '2025-10-14T14:15:00Z',
      status: 'reviewed',
    },
  ];

  // handleUpdateReportStatus(reportId: string, status: 'waiting' | 'approved' | 'declined') {
  //   this.reports = this.reports.map((r) => (r.id === reportId ? { ...r, status } : r));
  //   console.log('Update report status:', reportId, status);
  // }

  getSeverityColor(severity: string): string {
    return severity.toLowerCase(); // or your custom logic
  }

  handleApprove(id: string) {
    console.log('Approve', id);
  }

  handleDecline(id: string) {
    console.log('Decline', id);
  }

  handleDelete(id: string) {
    console.log('Delete', id);
  }
}
