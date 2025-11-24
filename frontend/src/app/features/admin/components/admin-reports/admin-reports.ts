import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { Report } from '../../models/report-model';
import { AdminApiService } from '../../services/admin-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { MatDialog } from '@angular/material/dialog';
import { Confirmation } from '../../../../shared/components/confirmation/confirmation';
import { PostDetail } from '../../../posts/components/post-detail/post-detail';
import { MatMenuModule } from '@angular/material/menu';
import { CommentDetail } from '../../../posts/components/comment-detail/comment-detail';

@Component({
  selector: 'app-admin-reports',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    Spinner,
    MatMenuModule,
  ],
  templateUrl: './admin-reports.html',
  styleUrl: './admin-reports.scss',
})
export class AdminReports {
  reports: Report[] = [];

  private apiService = inject(AdminApiService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);

  isLoading: boolean = true;
  isLoadingMore = true;
  noMoreReports = false;

  private limit: number = 9;
  private scrollDistance = 0.8;
  private throttle: number = 300;
  private isThrottled = false;
  private lastReportTime: string | null = null;

  ngOnInit(): void {
    this.loadReports();
  }

  private loadReports() {
    this.apiService.fetchReports(this.lastReportTime, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0) {
          this.noMoreReports = true;
          this.isLoading = false;
          this.isLoadingMore = false;
          return;
        }

        this.lastReportTime = response.at(-1)?.createdAt ?? null;
        this.reports.push(...response);
        this.isLoading = false;
        this.isLoadingMore = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.log('Failed to fetch reports:', e);
        this.isLoading = false;
        this.isLoadingMore = false;
      },
    });
  }

  openUserCardDialog(username: string | undefined) {
    if (!username) return;
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openPostDetail(postId: string): void {
    this.dialog.open(PostDetail, {
      data: { postId },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });
  }

  openCommentDetail(commentId: string): void {
    this.dialog.open(CommentDetail, {
      data: { commentId },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });
  }

  getSeverityColor(severity: string): string {
    return severity.toLowerCase();
  }

  handleApprove(id: string) {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to approve this report ?` },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('Approve', id);
      }
    });
  }

  handleDecline(id: string) {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to decline this report ?` },
      panelClass: 'post-report-dialog',
    });
    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('Decline', id);
      }
    });
  }

  handleDelete(id: string) {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to delete this report ?` },
      panelClass: 'post-report-dialog',
    });
    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('Delete', id);
      }
    });
  }

  resetReports() {
    this.reports = [];
    this.lastReportTime = null;
    this.noMoreReports = false;
    this.isLoading = true;
    this.loadReports();
  }
}
