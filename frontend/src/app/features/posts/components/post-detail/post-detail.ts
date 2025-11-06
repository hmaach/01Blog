import { Component, EventEmitter, inject, Inject, Input, Output } from '@angular/core';
import { Post } from '../../models/post-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { StorageService } from '../../../../core/services/storage.service';
import { formatDate } from '../../../../shared/lib/date';
import { PostApiService } from '../../services/post-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { Confirmation } from '../../../../shared/components/confirmation/confirmation';

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
  styleUrls: ['./post-detail.scss'],
})
export class PostDetail {
  @Input() comments: Comment[] = mockComments;
  @Output() close = new EventEmitter<void>();

  private storageService = inject(StorageService);
  private postApi = inject(PostApiService);
  private toast = inject(ToastService);
  formatDate = formatDate;

  post!: Post;
  menuOpen = false;
  commentText = '';
  isAdmin: boolean = this.storageService.isAdmin();

  constructor(
    private dialogRef: MatDialogRef<PostDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { post: Post },
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.post = this.data?.post;
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  toggleLike() {
    if (!this.post.id) return;

    const previousLiked = this.post.isLiked;
    const previousLikesCount = this.post.likesCount;

    this.post.isLiked = !this.post.isLiked;
    this.post.likesCount += this.post.isLiked ? 1 : -1;

    this.postApi.likePost(this.post.id).subscribe({
      error: (e) => {
        this.post.isLiked = previousLiked;
        this.post.likesCount = previousLikesCount;

        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        console.error('Failed to like post:', e);
      },
    });
  }

  openUserCardDialog(username: string) {
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openReportDialog(): void {
    this.dialog.open(ReportPost, {
      data: { postId: this.post.id },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
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

  handleDelete() {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: 'Are you sure you want to delete?' },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.postApi.deletePost(this.post.id).subscribe({
          next: () => {
            this.toast.show('Post deleted seccufully!', 'success');
            this.dialogRef.close({ action: 'delete', postId: this.post.id });
          },
          error: (e) => {
            this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
            console.error('Failed to delete post:', e);
          },
        });
      }
    });
  }
}
