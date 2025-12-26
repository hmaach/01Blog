import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { formatDate } from '../../lib/date';
import { Notification } from '../../../core/models/notification.model';
import { BlobService } from '../../../core/services/blob.service';
import { ToastService } from '../../../core/services/toast.service';
import { MatDialog } from '@angular/material/dialog';
import { NotificationApiService } from '../../../core/services/notification-api.service';
import { Spinner } from '../spinner/spinner';
import { PostDetail } from '../../../features/posts/components/post-detail/post-detail';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule, Spinner],
  templateUrl: './notifications.html',
  styleUrl: './notifications.scss',
})
export class Notifications {
  notifications: Notification[] = [];
  formatDate = formatDate;

  private apiService = inject(NotificationApiService);
  private blobService = inject(BlobService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);
  private observer!: IntersectionObserver;

  isLoading: boolean = true;
  isLoadingMore: boolean = true;
  noMoreNotif: boolean = false;

  private isBrowser: boolean;
  private limit: number = 15;
  private isThrottled: boolean = false;
  private throttle: number = 300;
  private lastNotifTime: string | null = null;

  constructor() {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  ngOnInit() {
    this.loadNotifications();
  }

  ngAfterViewInit() {
    if (!this.isBrowser) {
      return;
    }
    this.observer = new IntersectionObserver((entries, observer) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && !this.isLoading) {
          console.log('targetsfqsdf');

          if (this.isThrottled) return;

          this.isThrottled = true;

          setTimeout(() => {
            this.isThrottled = false;

            if (!this.noMoreNotif && !this.isLoadingMore) {
              this.isLoadingMore = true;
              this.loadNotifications();
            }
          }, this.throttle);
        }
      });
    });

    const target = document.getElementById('scroll-trigger');
    if (target) {
      this.observer.observe(target);
    }
  }

  private loadNotifications() {
    this.apiService.fetch(this.lastNotifTime, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0) {
          this.noMoreNotif = true;
          this.isLoading = false;
          this.isLoadingMore = false;
          return;
        }
        response.forEach((notif) => {
          if (notif.author?.avatarUrl) {
            this.blobService.loadBlob(notif.author?.avatarUrl).subscribe({
              next: (url) => {
                notif.author.avatarUrl = url;
              },
            });
          }
        });

        this.lastNotifTime = response.at(-1)?.createdAt ?? null;
        this.notifications.push(...response);
        this.isLoading = false;
        this.isLoadingMore = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  handleClickNotif(notifId: string, postId: string): void {
    this.dialog.open(PostDetail, {
      data: { postId },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });

    this.apiService.markSeen(notifId).subscribe({
      next: () => {
        const notif = this.notifications.find((n) => n.id === notifId);
        if (notif) {
          notif.seen = true;
        }
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }
}
