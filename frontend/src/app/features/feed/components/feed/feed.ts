import { Component, HostListener, inject } from '@angular/core';
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
  posts: Post[] = [];
  isLoading: boolean = true;
  isLoadingMore = true;
  noMorePosts = false;

  private page: number = 0;
  private limit: number = 8;
  private scrollDistance = 0.8;
  private throttle: number = 300;
  private isThrottled = false;

  private postApi = inject(PostApiService);
  private toast = inject(ToastService);

  ngOnInit(): void {
    this.loadPosts();
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: any) {
    if (this.isThrottled) return;
    this.isThrottled = true;

    setTimeout(() => {
      this.isThrottled = false;
      const scrollPosition = window.scrollY + window.innerHeight;
      const documentHeight = document.documentElement.scrollHeight;

      if (
        !this.noMorePosts &&
        !this.isLoadingMore &&
        scrollPosition >= documentHeight * this.scrollDistance
      ) {
        this.isLoadingMore = true;
        this.page += 1;
        this.loadPosts();
      }
    }, this.throttle);
  }

  private loadPosts() {
    this.postApi.fetchFeedPosts(this.page, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0) {
          this.noMorePosts = true;
          this.isLoading = false;
          this.isLoadingMore = false;
          return;
        }
        this.posts.push(...response);
        this.isLoading = false;
        this.isLoadingMore = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.log('Failed to fetch posts:', e);
        this.isLoading = false;
        this.isLoadingMore = false;
      },
    });
  }
}
