import { Component, inject, Input } from '@angular/core';
import { MarkdownModule } from 'ngx-markdown';
import { ProfileApiService } from '../../services/profile-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { CommonModule } from '@angular/common';
import { Spinner } from '../../../../shared/components/spinner/spinner';

@Component({
  selector: 'app-profile-readme',
  imports: [MarkdownModule, CommonModule, Spinner],
  templateUrl: './profile-readme.html',
  styleUrl: './profile-readme.scss',
})
export class ProfileReadme {
  @Input() userId?: string;
  private profileService = inject(ProfileApiService);
  private toast = inject(ToastService);
  readmeData?: string;
  isLoading: boolean = true;

  ngOnInit() {
    if (this.userId) {
      this.loadReadme();
    }
  }

  private loadReadme() {
    this.profileService.fetchUserReadme(this.userId!).subscribe({
      next: (response) => {
        this.readmeData = response;
        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.log('Failed to fetch user readme:', e);
        this.isLoading = false;
      },
    });

  }
}
