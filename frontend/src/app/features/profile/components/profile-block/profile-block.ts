import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { PostList } from '../../../posts/components/post-list/post-list';
import { Post } from '../../../posts/models/post-model';
import { ToastService } from '../../../../core/services/toast.service';
import { PostApiService } from '../../../posts/services/post-api.service';
import { Spinner } from "../../../../shared/components/spinner/spinner";

@Component({
  selector: 'app-profile-block',
  imports: [CommonModule, PostList, Spinner],
  templateUrl: './profile-block.html',
  styleUrl: './profile-block.scss',
})
export class ProfileBlock {
  @Input() username!: string;
  posts?: Post[];
  isLoading: boolean = true;

  private postApi = inject(PostApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    if (this.username) {
      this.loadPosts();
    }
  }

  private loadPosts() {
    this.postApi.fetchUserPosts(this.username).subscribe({
      next: (response) => {
        this.posts = response;
        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.log('Failed to fetch posts:', e);
        this.isLoading = false;
      },
    });
  }
}
