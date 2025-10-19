import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { Post } from '../../../../core/models/post-model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Comment } from '../../../../core/models/comment-model';
import { mockComments } from '../../../../shared/lib/mock-data';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatStepperModule } from '@angular/material/stepper';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    MatButtonModule,
  ],
  templateUrl: './post-detail.html',
  styleUrls: ['./post-detail.scss']
})
export class PostDetail {
  // @Input() post!: Post;
  @Input() comments: Comment[] = mockComments;
  @Output() close = new EventEmitter<void>();
  post!: Post;

  constructor(
    private dialogRef: MatDialogRef<PostDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { post: Post }
  ) { }

  ngOnInit(): void {
    console.log('Received post:', this.data.post);
    this.post = this.data?.post;
  }


  menuOpen = false;
  reportModalOpen = false;
  reportCategory = 'spam';
  reportReason = '';
  commentText = '';

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  openReportModal(): void {
    this.reportModalOpen = true;
    this.menuOpen = false;
  }

  handleReport(): void {
    console.log('Report submitted:', { category: this.reportCategory, reason: this.reportReason });
    this.reportModalOpen = false;
    this.reportCategory = 'spam';
    this.reportReason = '';
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
