import { Component, Input, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { BlobService } from '../../../../core/services/blob.service';
import { UserResponse } from '../../models/user-response.model';
import { MediaPreview } from '../../../../shared/components/media-preview/media-preview';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { formatNumber } from '../../../../shared/lib/format';
import { RouterModule } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ReportDialog } from '../../../../shared/components/report-dialog/report-dialog';

@Component({
  selector: 'app-profile-card',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    RouterModule,
  ],
  templateUrl: './profile-card.html',
  styleUrls: ['./profile-card.scss'],
})
export class ProfileCard implements OnInit {
  private profileService = inject(ProfileApiService);
  private blobService = inject(BlobService);
  private toast = inject(ToastService);

  @Input() username?: string;
  @Input() user?: UserResponse;
  @Input() isDialog!: boolean;
  @Input() closeDialog?: () => void;

  avatarUrl?: string;
  formatNumber = formatNumber;

  constructor(private dialog: MatDialog) {}

  ngOnInit() {
    if (this.user) {
      if (this.user.avatarUrl) {
        this.blobService.loadBlob(this.user.avatarUrl).subscribe({
          next: (url) => {
            this.avatarUrl = url;
          },
        });
      }
    } else if (this.username) {
      this.loadUserProfile(this.username);
    }
  }

  private loadUserProfile(username: string) {
    this.profileService.fetchUserProfile(username).subscribe({
      next: (response) => {
        this.user = response;
        if (response.avatarUrl) {
          this.blobService.loadBlob(response.avatarUrl).subscribe({
            next: (url) => (this.avatarUrl = url),
          });
        }
      },
      error: (err) => {
        console.error('Card: Failed to fetch user profile:', err);
        this.toast.show('Failed to fetch user profile', 'error');
      },
    });
  }

  openReportDialog(): void {
    this.dialog.open(ReportDialog, {
      data: { reportType: 'user', reportedUserId: this.user?.id },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
    });
  }

  preview(media: string) {
    this.dialog.open(MediaPreview, {
      data: { media },
      panelClass: 'media-preview-dialog',
    });
  }
}
