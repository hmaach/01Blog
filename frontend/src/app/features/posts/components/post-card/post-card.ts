import { Component, inject, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { Post } from '../../models/post-model';
import { PostDetail } from '../post-detail/post-detail';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { Media } from '../../models/media-model';
import { BlobService } from '../../../../core/services/blob.service';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatDialogModule],
  templateUrl: './post-card.html',
  styleUrls: ['./post-card.scss'],
})
export class PostCard {
  @Input() post!: Post;

  private blobService = inject(BlobService);

  constructor(private dialog: MatDialog) {}

  ngOnInit() {
    if (this.post.media) {
      this.post.media.forEach((media) => {
        this.blobService.loadBlob(media.url).subscribe({
          next: (url) => {
            media.url = url;
          },
        });
      });
    }
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
  }

  openUserCardDialog(username: string) {
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openPostDetail(post: Post): void {
    this.dialog.open(PostDetail, {
      data: { post },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });
  }
}
