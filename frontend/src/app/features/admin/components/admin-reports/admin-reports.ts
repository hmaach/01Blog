import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, RouterLink } from '@angular/router';
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
  private route = inject(ActivatedRoute);

  isLoading: boolean = true;
  isLoadingMore = true;
  noMoreReports = false;

  totalUserReports!: number;
  totalPostReports!: number;
  totalCommentReports!: number;

  private limit: number = 9;
  private scrollDistance = 0.8;
  private throttle: number = 300;
  private isThrottled = false;
  private lastReportTime: string | null = null;

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.totalUserReports = params['totalUserReports'];
      this.totalPostReports = params['totalPostReports'];
      this.totalCommentReports = params['totalCommentReports'];
    });
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

  changeReportStatus(id: string, action: 'approve' | 'decline' | 'pending') {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to ${action} this report ?` },
      panelClass: 'post-report-dialog',
    });

    let status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
    if (action === 'decline') status = 'REJECTED';
    else if (action === 'approve') status = 'ACCEPTED';
    else if (action === 'pending') status = 'PENDING';
    else return;

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;

      this.apiService.changeReportStatus(id, status).subscribe({
        next: () => {
          const report = this.reports.find((r) => r.id === id);
          if (report) {
            report.status = status;
          }
          this.toast.show('Report status changed successfully', 'success');
        },
        error: (e) => {
          this.toast.show(e?.error?.message || 'Unkown error message', 'error');
        },
      });
    });
  }

  handleDelete(id: string) {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to delete this report ?` },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (!confirmed) return;

      this.apiService.deleteReport(id).subscribe({
        next: () => {
          const report = this.reports.find((r) => r.id === id);
          this.reports = this.reports.filter((r) => r.id !== id);

          if (report?.reportedType === 'POST') this.totalPostReports--;
          else if (report?.reportedType === 'COMMENT') this.totalCommentReports--;
          else if (report?.reportedType === 'USER') this.totalUserReports--;

          this.toast.show('Report deleted successfully', 'success');
        },
        error: (e) => {
          this.toast.show(e?.error?.message || 'Failed to delete report', 'error');
        },
      });
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
