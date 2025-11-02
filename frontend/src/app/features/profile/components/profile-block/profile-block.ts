import { CommonModule } from '@angular/common';
import { Component, HostListener, inject, Input } from '@angular/core';
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

  private page: number = 0;
  private limit: number = 8;
  private scrollDistance: number = 0.8;
  private isThrottled: boolean = false;
  private throttle: number = 300;
  private lastPostTime: string | null = null;

  private postApi = inject(PostApiService);
  private toast = inject(ToastService);

  ngOnInit() {
    if (this.username) {
      this.loadPosts();
    }
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
    this.postApi.fetchUserPosts(this.username, this.lastPostTime, this.limit).subscribe({
      next: (response) => {
        this.lastPostTime = response.at(-1)?.createdAt ?? null;
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
