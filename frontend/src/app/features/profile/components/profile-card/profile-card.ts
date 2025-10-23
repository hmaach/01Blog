import { Component, Inject, Input, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { BlobApiService } from '../../../../core/services/blob-api.service';
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
  private toast = inject(ToastService);
  private blobApi = inject(BlobApiService);

  @Input() username?: string;
  @Input() user?: UserResponse;
  @Input() isDialog!: boolean;

  avatarUrl?: string;

  ngOnInit() {
    if (this.user) {
      if (this.user.avatarUrl) this.loadAvatar(this.user.avatarUrl);
    } else if (this.username) {
      this.loadUserProfile(this.username);
    }
  }

  private loadUserProfile(username: string) {
    this.profileService.fetchUserProfile(username).subscribe({
      next: (response) => {
        this.user = response;
        if (response.avatarUrl) this.loadAvatar(response.avatarUrl);
      },
      error: (err) => {
        console.error('Failed to fetch user profile:', err);
        this.toast.show('Failed to fetch user profile', 'error');
      },
    });
  }

  private loadAvatar(avatarPath: string) {
    this.blobApi.fetch(avatarPath).subscribe({
      next: (blob) => {
        this.avatarUrl = URL.createObjectURL(blob);
      },
      error: (err) => {
        console.error('Failed to fetch avatar blob:', err);
        this.toast.show('Failed to fetch avatar', 'error');
      },
    });
  }
}
