import { Component, inject } from '@angular/core';
import { Post } from '../../../posts/models/post-model';
import { CommonModule } from '@angular/common';
import { PostList } from '../../../posts/components/post-list/post-list';
import { PostApiService } from '../../../posts/services/post-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { Spinner } from '../../../../shared/components/spinner/spinner';

@Component({
  selector: 'app-feed',
  imports: [CommonModule, PostList, Spinner],
  templateUrl: './feed.html',
  styleUrl: './feed.scss',
})
export class Feed {
  posts?: Post[];
  isLoading: boolean = true;
  postApi = inject(PostApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    this.loadPosts();
  }

  private loadPosts() {
    this.postApi.fetchFeedPosts().subscribe({
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
