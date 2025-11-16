import { Component } from '@angular/core';
import { mockCurrentUser, mockReports, mockUsers } from '../../../../shared/lib/mock-data';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-admin-main',
  imports: [CommonModule, MatCardModule],
  templateUrl: './admin-main.html',
  styleUrl: './admin-main.scss',
})
export class AdminMain {
  currentUser = mockCurrentUser;
  users = [...mockUsers];
  reports = [...mockReports];

  totalPosts = 156;

  get totalUsers() {
    return this.users.length;
  }

  get totalReports() {
    return this.reports.length;
  }

  get pendingReports() {
    return this.reports.filter((r) => r.status === 'waiting').length;
  }

  handleBanUser(userId: string) {
    console.log('Ban user:', userId);
  }

  handleDeleteUser(userId: string) {
    this.users = this.users.filter((u) => u.id !== userId);
    console.log('Delete user:', userId);
  }

  handleChangeRole(userId: string, newRole: 'admin' | 'user') {
    this.users = this.users.map((u) => (u.id === userId ? { ...u, role: newRole } : u));
    console.log('Change role:', userId, newRole);
  }

  handleUpdateReportStatus(reportId: string, status: 'waiting' | 'approved' | 'declined') {
    this.reports = this.reports.map((r) => (r.id === reportId ? { ...r, status } : r));
    console.log('Update report status:', reportId, status);
  }
}
