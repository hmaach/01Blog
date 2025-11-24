import { Component, EventEmitter, Inject, inject, Output } from '@angular/core';
import { StorageService } from '../../../../core/services/storage.service';
import { CommentApiService } from '../../services/comment-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { BlobService } from '../../../../core/services/blob.service';
import { formatDate } from '../../../../shared/lib/date';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Comment } from '../../models/comment-model';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { PostDetail } from '../post-detail/post-detail';

@Component({
  selector: 'app-comment-detail',
  imports: [
    MatSelectModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    CommonModule,
    MatProgressSpinnerModule,
    Spinner,
  ],
  templateUrl: './comment-detail.html',
  styleUrl: './comment-detail.scss',
})
export class CommentDetail {
  @Output() close = new EventEmitter<void>();

  private blobService = inject(BlobService);
  private storageService = inject(StorageService);
  private commentApi = inject(CommentApiService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);

  commetId!: string;
  comment!: Comment;
  menuOpen = false;
  isAdmin: boolean = this.storageService.isAdmin();
  formatDate = formatDate;
  isLoading: boolean = true;

  constructor(
    private dialogRef: MatDialogRef<CommentDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { commentId: string }
  ) {}

  ngOnInit(): void {
    if (this.data.commentId) {
      this.loadComment();
    }
  }

  loadComment() {
    if (!this.data.commentId) return;

    this.commentApi.fetchComment(this.data.commentId).subscribe({
      next: (comment) => {
        if (comment) {
          this.comment = comment;
          this.isLoading = false;
          if (comment.author?.avatarUrl) {
            this.blobService.loadBlob(comment.author?.avatarUrl).subscribe({
              next: (url) => {
                comment.author.avatarUrl = url;
              },
            });
          }
        }
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  openUserCardDialog(username: string) {
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openPostDetail(postId: string): void {
    this.dialog.open(PostDetail, {
      data: { postId },
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-detail-dialog',
    });
  }

  handleDeleteComment() {
    if (!this.data.commentId) return;

    this.commentApi.deleteComment(this.data.commentId).subscribe({
      next: () => {
        this.toast.show('Comment deleted successfully!', 'success');
        this.closeDialog();
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
