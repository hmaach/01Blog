import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { PostList } from '../../../posts/components/post-list/post-list';
import { Post } from '../../../posts/models/post-model';
import { ToastService } from '../../../../core/services/toast.service';
import { PostApiService } from '../../../posts/services/post-api.service';
import { Spinner } from '../../../../shared/components/spinner/spinner';

@Component({
  selector: 'app-profile-block',
  imports: [CommonModule, PostList, Spinner],
  templateUrl: './profile-block.html',
  styleUrl: './profile-block.scss',
})
export class ProfileBlock {
  @Input() username!: string;
  posts: Post[] = [];
  isLoading: boolean = true;
  isLoadingMore: boolean = true;
  noMorePosts: boolean = false;

  private isBrowser: boolean;
  private page: number = 0;
  private limit: number = 8;
  private isThrottled: boolean = false;
  private throttle: number = 300;
  private lastPostTime: string | null = null;

  private postApi = inject(PostApiService);
  private toast = inject(ToastService);
  private observer!: IntersectionObserver;

  constructor() {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  ngOnInit() {
    if (this.username) {
      this.loadPosts();
    }
  }

  ngAfterViewInit() {
    if (!this.isBrowser) {
      return;
    }
    this.observer = new IntersectionObserver((entries, observer) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          if (this.isThrottled) return;

          this.isThrottled = true;

          setTimeout(() => {
            this.isThrottled = false;

            if (!this.noMorePosts && !this.isLoadingMore) {
              this.isLoadingMore = true;
              this.page += 1;
              this.loadPosts();
            }
          }, this.throttle);
        }
      });
    });

    const target = document.getElementById('scroll-trigger');
    if (target) {
      this.observer.observe(target);
    }
  }

  private loadPosts() {
    this.postApi.fetchUserPosts(this.username, this.lastPostTime, this.limit).subscribe({
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
        this.isLoading = false;
      },
    });
  }
}
