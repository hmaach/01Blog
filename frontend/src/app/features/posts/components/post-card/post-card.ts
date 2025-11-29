import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { Post } from '../../models/post-model';
import { PostDetail } from '../post-detail/post-detail';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { BlobService } from '../../../../core/services/blob.service';
import { formatDate } from '../../../../shared/lib/date';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatDialogModule],
  templateUrl: './post-card.html',
  styleUrls: ['./post-card.scss'],
})
export class PostCard {
  @Input() post!: Post;
  @Output() postDeleted = new EventEmitter<string>();
  @Output() postUpdated = new EventEmitter<Post>();

  private blobService = inject(BlobService);
  private dialog = inject(MatDialog);

  formatDate = formatDate;

  ngOnInit() {
    if (this.post.firstMedia) {
      this.blobService.loadBlob(this.post.firstMedia.url).subscribe({
        next: (url) => {
          this.post.firstMedia!.url = url;
        },
      });
    }

    if (this.post.author.avatarUrl) {
      this.blobService.loadBlob(this.post.author.avatarUrl).subscribe({
        next: (url) => {
          this.post.author.avatarUrl = url;
        },
      });
    }
  }

  openUserCardDialog(username: string) {
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openPostDetail(post: Post): void {
    const dialogRef = this.dialog.open(PostDetail, {
      data: { post },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });

    // Notify parent that a post was deleted
    dialogRef.afterClosed().subscribe((result) => {
      if (result?.action === 'delete') {
        this.postDeleted.emit(result.postId);
      } else if (result?.action === 'update') {
        this.postUpdated.emit(result.post);
      }
    });
  }
}
