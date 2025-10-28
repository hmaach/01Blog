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

@Component({
  selector: 'app-post-list',
  imports: [PostCard, CommonModule, MatMenuModule, MatIconModule, MatDialogModule, MatButtonModule],
  templateUrl: './post-list.html',
  styleUrl: './post-list.scss',
})
export class PostList {
  @Input() posts?: Post[];

  private storageService = inject(StorageService);

  constructor(private dialog: MatDialog) {}

  openCreatePostDialog(): void {
    const dialogRef = this.dialog.open(PostForm, {
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
}
