import { Component, inject, Input } from '@angular/core';
import { PostCard } from '../post-card/post-card';
import { Post } from '../../models/post-model';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PostForm } from '../post-form/post-form';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { StorageService } from '../../../../core/services/storage.service';
import { Author } from '../../models/author-model';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post-list',
  imports: [
    PostCard,
    Spinner,
    CommonModule,
    MatMenuModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
    MatProgressBarModule,
  ],
  templateUrl: './post-list.html',
  styleUrl: './post-list.scss',
})
export class PostList {
  @Input() posts?: Post[];
  @Input() isLoadingMore?: boolean;
  @Input() noMorePosts?: boolean;

  private storageService = inject(StorageService);
  private route = inject(ActivatedRoute);

  isOwner: boolean = false;
  isProfile: boolean = false;
  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {
    const snapshot = this.route.snapshot;
    const currentPath = snapshot.url.map((segment) => segment.path).join('/');

    const username = snapshot.paramMap.get('username')!;
    const currUserUsername = this.storageService.getCurrentUserInfo()?.username;

    this.isOwner = username === currUserUsername;
    this.isProfile = currentPath.startsWith('profile');
  }

  openCreatePostDialog(): void {
    const dialogRef = this.dialog.open(PostForm, {
      data: { action: 'create' },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-form-dialog',
    });

    dialogRef.afterClosed().subscribe((newPost: Post) => {
      if (newPost) {
        const author: Author = {
          id: this.storageService.getCurrentUserInfo()?.id || '',
          name: this.storageService.getCurrentUserInfo()?.name || '',
          username: this.storageService.getCurrentUserInfo()?.username || '',
          avatarUrl: this.storageService.getUserAvatarUrl() || null,
        };

        newPost.author = author;
        this.posts?.unshift(newPost);
      }
    });
  }

  updatePost(post: Post) {
    if (!post) return;

    const index = this.posts?.findIndex((p) => p.id === post.id);
    if (index === undefined || index === -1 || !this.posts) return;

    this.posts[index] = post;
    if (post.media && post.media.length > 0) {
      this.posts[index].firstMedia = post.media[0];
    }
  }

  removePost(postId: string) {
    if (!this.posts) return;
    this.posts = this.posts.filter((p) => p.id !== postId);
  }
}
