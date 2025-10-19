import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { Post } from '../../models/post-model';
import { PostDetail } from '../post-detail/post-detail';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatDialogModule],
  templateUrl: './post-card.html',
  styleUrls: ['./post-card.scss'],
})
export class PostCard {
  @Input() post!: Post;

  constructor(private dialog: MatDialog) { }

  openPostDetail(post: Post): void {
    this.dialog.open(PostDetail, {
      data: { post },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog'
    });
  }
}
