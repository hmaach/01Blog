import { Component, inject, Input } from '@angular/core';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';

@Component({
  selector: 'app-profile-readme',
  imports: [],
  templateUrl: './profile-readme.html',
  styleUrl: './profile-readme.scss',
})
export class ProfileReadme {
  @Input() userId?: string;
  readmaData?: string;
  private profileService = inject(ProfileApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    if (this.userId) {
      this.loadReadme();
    }
  }

  private loadReadme() {
    console.log(this.userId);
    this.profileService.fetchUserReadme(this.userId!).subscribe({
      next: (response) => {
        console.log(response);

        this.readmaData = response;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.error('Failed to fetch user readme:', e);
      },
    });
  }
}
