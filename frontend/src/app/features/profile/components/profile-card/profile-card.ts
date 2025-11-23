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
import { StorageService } from '../../../../core/services/storage.service';
import { Confirmation } from '../../../../shared/components/confirmation/confirmation';
import { MatMenuModule } from '@angular/material/menu';
import { AdminApiService } from '../../../admin/services/admin-api.service';

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
    MatMenuModule,
  ],
  templateUrl: './profile-card.html',
  styleUrls: ['./profile-card.scss'],
})
export class ProfileCard implements OnInit {
  private profileService = inject(ProfileApiService);
  private blobService = inject(BlobService);
  private storageService = inject(StorageService);
  private adminService = inject(AdminApiService);
  private toast = inject(ToastService);

  @Input() username?: string;
  @Input() user?: UserResponse;
  @Input() isDialog!: boolean;
  @Input() closeDialog?: () => void;

  isAdmin: boolean = this.storageService.getUserRole() === 'ADMIN';
  isLoading: boolean = false;
  avatarUrl?: string;
  formatNumber = formatNumber;

  constructor(private dialog: MatDialog) {}

  ngOnInit() {
    console.log(this.user?.relation);

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
        this.toast.show('Failed to fetch user profile', 'error');
      },
    });
  }

  openReportDialog(): void {
    this.dialog.open(ReportDialog, {
      data: { reportType: 'user', reportedUserId: this.user?.id, closeDialog: this.closeDialog },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
    });
  }

  toggleSubscription() {
    if (this.user?.id) {
      this.isLoading = true;
      switch (this.user?.relation) {
        case 'unsubscribed':
          this.profileService.subscribe(this.user.id).subscribe({
            next: () => {
              if (this.user) this.user.relation = 'subscribed';
              this.isLoading = false;
            },
            error: (e) => {
              this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
              this.isLoading = false;
            },
          });
          break;
        case 'subscribed':
          this.profileService.unsubscribe(this.user.id).subscribe({
            next: () => {
              if (this.user) this.user.relation = 'unsubscribed';
              this.isLoading = false;
            },
            error: (e) => {
              this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
              this.isLoading = false;
            },
          });
          break;
      }
    }
  }

  preview(media: string) {
    this.dialog.open(MediaPreview, {
      data: { media },
      panelClass: 'media-preview-dialog',
    });
  }

  handleDeleteUser() {
    if (!this.isAdmin) return;
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to DELETE this user ?` },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('Delete', this.user?.id);
      }
    });
  }
  toggleUserRole() {
    if (!this.isAdmin) return;
    const dialogRef = this.dialog.open(Confirmation, {
      data: {
        message: `Are you sure you want to make this user ${
          this.user?.role === 'ADMIN' ? 'User' : 'Admin'
        } ?`,
      },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('change role', this.user?.id);
      }
    });
  }
  toggleUserBanning() {
    if (!this.isAdmin) return;
    const dialogRef = this.dialog.open(Confirmation, {
      data: {
        message: `Are you sure you want to ${
          this.user?.status === 'active' ? 'Ban' : 'Unban'
        } this user ?`,
      },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        console.log('Ban/Unban', this.user?.id);
      }
    });
  }
}
