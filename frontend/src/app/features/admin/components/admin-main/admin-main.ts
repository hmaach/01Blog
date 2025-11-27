import { Component, inject } from '@angular/core';
import { mockCurrentUser, mockReports, mockUsers } from '../../../../shared/lib/mock-data';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { ToastService } from '../../../../core/services/toast.service';
import { AdminApiService } from '../../services/admin-api.service';
import { MatDialog } from '@angular/material/dialog';
import { AdminStats } from '../../models/stats-model';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';

@Component({
  selector: 'app-admin-main',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    RouterLink,
    MatButtonModule,
    MatChipsModule,
  ],
  templateUrl: './admin-main.html',
  styleUrl: './admin-main.scss',
})
export class AdminMain {
  stats!: AdminStats;
  currentUser = mockCurrentUser;
  users = [...mockUsers];
  reports = [...mockReports];

  private apiService = inject(AdminApiService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);

  totalPosts = 156;
  isLoading: boolean = true;

  ngOnInit(): void {
    this.loadStats();
  }

  private loadStats() {
    this.apiService.fetchStats().subscribe({
      next: (response) => {
        this.stats = response;
        console.log(response);

        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

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

  openUserCardDialog(username: string | undefined) {
    if (!username) return;
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }
}
