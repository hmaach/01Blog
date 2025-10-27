import { Component, inject } from '@angular/core';
import { Post } from '../../../posts/models/post-model';
import { PostList } from '../../../posts/components/post-list/post-list';
import { CommonModule } from '@angular/common';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { ToastService } from '../../../../core/services/toast.service';
import { PostApiService } from '../../../posts/services/post-api.service';

@Component({
  selector: 'app-explore-feed',
  imports: [CommonModule, PostList, Spinner],
  templateUrl: './explore-feed.html',
  styleUrl: './explore-feed.scss',
})
export class ExploreFeed {
  posts?: Post[];
  isLoading: boolean = true;
  postApi = inject(PostApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    this.loadPosts();
  }

  private loadPosts() {
    this.postApi.fetchExplorePosts().subscribe({
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
