import { Component, Inject, Input, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { BlobService } from '../../../../core/services/blob.service';
import { UserResponse } from '../../models/user-response.model';

@Component({
  selector: 'app-profile-card',
  standalone: true,
  imports: [CommonModule, MatCardModule],
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

  avatarUrl?: string;

  ngOnInit() {
    if (this.user) {
      if (this.user.avatarUrl) {
        this.blobService.loadBlob(this.user.avatarUrl).subscribe({
          next: (url) => (this.avatarUrl = url),
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
        console.error('Failed to fetch user profile:', err);
        this.toast.show('Failed to fetch user profile', 'error');
      },
    });
  }
}
