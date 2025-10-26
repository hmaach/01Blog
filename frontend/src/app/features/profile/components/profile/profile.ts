import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfileApiService } from '../../services/profile-api.service';
import { UserResponse } from '../../models/user-response.model';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { ProfileReadme } from '../profile-readme/profile-readme';
import { ToastService } from '../../../../core/services/toast.service';
import { ProfileBlock } from '../profile-block/profile-block';
import { ProfileCard } from '../profile-card/profile-card';
import { HttpErrorResponse } from '@angular/common/http';
import { ProfileNotFound } from '../profile-not-found/profile-not-found';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { BlobService } from '../../../../core/services/blob.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    ProfileReadme,
    ProfileBlock,
    ProfileCard,
    ProfileNotFound,
    Spinner,
  ],
  templateUrl: './profile.html',
  styleUrls: ['./profile.scss'],
})
export class Profile {
  username!: string;
  user?: UserResponse;
  avatarUrl?: string;
  notFound: boolean = false;
  isLoading: boolean = true;

  private route = inject(ActivatedRoute);
  private blobService = inject(BlobService);
  private profileService = inject(ProfileApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    this.username = this.route.snapshot.paramMap.get('username')!;
    this.loadUserProfile();
  }

  private loadUserProfile() {
    this.profileService.fetchUserProfile(this.username).subscribe({
      next: (response) => {
        this.user = response;

        if (this.user?.avatarUrl) {
          this.blobService.loadBlob(this.user.avatarUrl).subscribe({
            next: (url) => (this.avatarUrl = url),
          });
        }

        this.isLoading = false;
      },
      error: (err: HttpErrorResponse) => {
        if (err.status == 404) {
          this.notFound = true;
        } else {
          console.log('Profile: Failed to fetch user profile:', err);
          this.toast.show('Failed to fetch user profile', 'error');
        }
        this.isLoading = false;
      },
    });
  }
}
