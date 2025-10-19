import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { Post } from '../../models/post-model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Comment } from '../../models/comment-model';
import { mockComments } from '../../../../shared/lib/mock-data';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { ReportPost } from '../../../report/components/report-post/report-post';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './post-detail.html',
  styleUrls: ['./post-detail.scss']
})
export class PostDetail {
  @Input() comments: Comment[] = mockComments;
  @Output() close = new EventEmitter<void>();

  post!: Post;
  menuOpen = false;
  commentText = '';

  constructor(
    private dialogRef: MatDialogRef<PostDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { post: Post },
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.post = this.data?.post;
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  openReportDialog(): void {
    this.dialog.open(ReportPost, {
      data: { postId: this.post.id },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog'
    });
  }

  handleComment(): void {
    console.log('Comment submitted:', this.commentText);
    this.commentText = '';
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
