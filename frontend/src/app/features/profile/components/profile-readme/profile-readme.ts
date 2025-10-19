import { Component, inject, Input } from '@angular/core';
import { MarkdownModule } from 'ngx-markdown';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-readme',
  imports: [MarkdownModule, CommonModule],
  templateUrl: './profile-readme.html',
  styleUrl: './profile-readme.scss',
})
export class ProfileReadme {
  @Input() userId?: string;
  readmeData?: string;
  private profileService = inject(ProfileApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    // if (this.userId) {
      this.loadReadme();
    // }
  }

  private loadReadme() {
    this.profileService.fetchUserReadme(this.userId!).subscribe({
      next: (response) => {
        this.readmeData = response;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.log('Failed to fetch user readme:', e);
      },
    });
  }
}
