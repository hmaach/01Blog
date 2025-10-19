import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfileApiService } from '../../services/profile-api.service';
import { UserResponse } from '../../models/user-response.model';
import { CommonModule } from '@angular/common';
import { BlobApiService } from '../../../../core/services/blob-api.service';
import { MatIcon } from '@angular/material/icon';
import { MatCard } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { ProfileReadme } from "../profile-readme/profile-readme";
import { ToastService } from '../../../../core/services/toast.service';
import { ProfileBlock } from '../profile-block/profile-block';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, MatIcon, MatCard, MatTabsModule, ProfileReadme, ProfileBlock],
  templateUrl: './profile.html',
  styleUrls: ['./profile.scss'],
})
export class Profile {
  username!: string;
  user?: UserResponse;
  avatarUrl?: string;

  private route = inject(ActivatedRoute);
  private profileService = inject(ProfileApiService);
  private toast = inject(ToastService);
  private blobApi = inject(BlobApiService);

  ngOnInit() {
    this.username = this.route.snapshot.paramMap.get('username')!;
    this.loadUserProfile();
  }

  private loadUserProfile() {
    this.profileService.fetchUserProfile(this.username).subscribe({
      next: (response) => {
        this.user = response;

        if (this.user?.avatarUrl) {
          this.loadAvatar(this.user.avatarUrl);
        }
      },
      error: (err) => {
        console.log('Failed to fetch user profile:', err);
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
        console.log('Failed to fetch avatar blob:', err);
        this.toast.show('Failed to fetch avatar', 'error');
      },
    });
  }
}
