import { Component, HostListener, inject } from '@angular/core';
import { Post } from '../../../posts/models/post-model';
import { PostList } from '../../../posts/components/post-list/post-list';
import { CommonModule } from '@angular/common';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { ToastService } from '../../../../core/services/toast.service';
import { FeedApiService } from '../../services/feed-api.service';

@Component({
  selector: 'app-explore-feed',
  imports: [CommonModule, PostList, Spinner],
  templateUrl: './explore-feed.html',
  styleUrl: './explore-feed.scss',
})
export class ExploreFeed {
  posts: Post[] = [];
  isLoading: boolean = true;
  isLoadingMore = false;
  noMorePosts = false;

  private page: number = 0;
  private limit: number = 9;
  private scrollDistance = 0.8;
  private throttle: number = 300;
  private isThrottled = false;
  private lastPostTime: string | null = null;

  private feedApi = inject(FeedApiService);
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
    this.feedApi.fetchExplorePosts(this.lastPostTime, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0) {
          this.noMorePosts = true;
          this.isLoading = false;
          this.isLoadingMore = false;
          return;
        }

        this.lastPostTime = response.at(-1)?.createdAt ?? null;
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

  resetExplore() {
    this.posts = [];
    this.lastPostTime = null;
    this.noMorePosts = false;
    this.isLoading = true;
    this.loadPosts();
  }
}
