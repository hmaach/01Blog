import { Component, EventEmitter, inject, Inject, Input, Output } from '@angular/core';
import { Post } from '../../models/post-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Comment } from '../../models/comment-model';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { StorageService } from '../../../../core/services/storage.service';
import { formatDate } from '../../../../shared/lib/date';
import { PostApiService } from '../../services/post-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { Confirmation } from '../../../../shared/components/confirmation/confirmation';
import { PostForm } from '../post-form/post-form';
import { BlobService } from '../../../../core/services/blob.service';
import { ReportDialog } from '../../../../shared/components/report-dialog/report-dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommentApiService } from '../../services/comment-api.service';
import { Author } from '../../models/author-model';
import { Spinner } from "../../../../shared/components/spinner/spinner";

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
    MatProgressSpinnerModule,
    Spinner
],
  templateUrl: './post-detail.html',
  styleUrls: ['./post-detail.scss'],
})
export class PostDetail {
  @Input() comments?: Comment[];
  @Output() close = new EventEmitter<void>();

  private storageService = inject(StorageService);
  private postApi = inject(PostApiService);
  private commentApi = inject(CommentApiService);
  private toast = inject(ToastService);
  private blobService = inject(BlobService);

  postId!: string;
  post!: Post;
  menuOpen = false;
  isAdmin: boolean = this.storageService.isAdmin();
  formatDate = formatDate;
  isLoading: boolean = true;
  isMediaLoading: boolean = true;

  private commentsLimit: number = 10;
  private lastCommentTime: string | null = null;
  commentText = '';
  isCommentsLoading: boolean = true;

  constructor(
    private dialogRef: MatDialogRef<PostDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { post: Post; postId: string },
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    if (this.data?.post) {
      this.post = this.data.post;
      this.postId = this.data.post.id;
      this.isLoading = false;
    } else if (this.data?.postId) {
      this.postId = this.data.postId;
    } else {
      return;
    }

    // Now proceed with loading post details
    if (this.postId) {
      this.loadPostDetail();
    }

    // this.loadComments();
  }

  toggleLike() {
    if (!this.postId) return;

    const previousLiked = this.post.isLiked;
    const previousLikesCount = this.post.likesCount;

    this.post.isLiked = !this.post.isLiked;
    this.post.likesCount += this.post.isLiked ? 1 : -1;

    this.postApi.likePost(this.postId).subscribe({
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
    if (!this.post) return;

    this.dialog.open(ReportDialog, {
      data: {
        reportType: 'post',
        reportedUserId: this.post.author.id,
        reportedPostId: this.postId,
      },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
    });
  }

  openEditPostDialog(): void {
    const dialogRef = this.dialog.open(PostForm, {
      data: { action: 'edit', post: this.post },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((post: Post) => {
      if (post) {
        this.post.title = post.title;
        this.post.body = post.body;
        this.post.isLiked = post.isLiked;
        this.post.likesCount = post.likesCount;
        this.post.commentsCount = post.commentsCount;
        this.post.impressionsCount = post.impressionsCount;
        this.post.media = post.media ? [...post.media] : [];

        this.post.media.forEach((media) => {
          this.blobService.loadBlob(media.url).subscribe({
            next: (url) => (media.url = url),
          });
        });
      }
    });
  }

  //TODO: update the media and the comments count in the posts list
  handleCreateComment(): void {
    this.commentApi.createComment(this.postId, this.commentText).subscribe({
      next: async (newComment: Comment) => {
        if (newComment) {
          const author: Author = {
            id: this.storageService.getCurrentUserInfo()?.id || '',
            name: this.storageService.getCurrentUserInfo()?.name || '',
            username: this.storageService.getCurrentUserInfo()?.username || '',
            avatarUrl: this.storageService.getUserAvatarUrl() || null,
          };

          if (author.avatarUrl) {
            await this.blobService.loadBlob(author.avatarUrl).subscribe({
              next: (url) => {
                author.avatarUrl = url;
              },
            });
          }

          newComment.author = author;
          this.post.commentsCount += 1;
          this.commentText = '';
          this.comments?.unshift(newComment);
        }
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
      },
    });
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  private loadPostDetail() {
    const postId: string = this.data.post.id ? this.data.post.id : this.data.postId;

    if (!postId) return;
    this.postApi.fetchPostDetail(postId).subscribe({
      next: (post) => {
        this.post.media = post.media;
        if (this.post.media) {
          this.post.media.forEach((media) => {
            this.blobService.loadBlob(media.url).subscribe({
              next: (url) => {
                media.url = url;
              },
            });
          });
          this.isMediaLoading = false;
        }
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isMediaLoading = false;
      },
    });
  }

  private loadComments() {
    if (!this.post) return;
    this.commentApi.fetchComments(this.postId, this.lastCommentTime, this.commentsLimit).subscribe({
      next: (comments) => {
        if (comments.length > 0) {
          this.comments = comments;
          this.lastCommentTime = comments.at(-1)?.createdAt ?? null;
          comments.forEach((comment) => {
            if (comment.author?.avatarUrl) {
              this.blobService.loadBlob(comment.author?.avatarUrl).subscribe({
                next: (url) => {
                  comment.author.avatarUrl = url;
                },
              });
            }
          });

          this.comments.push(...comments);
          this.isCommentsLoading = false;
        }
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isCommentsLoading = false;
      },
    });
  }

  handleDeletePost() {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: 'Are you sure you want to delete?' },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.postApi.deletePost(this.postId).subscribe({
          next: () => {
            this.toast.show('Post deleted seccufully!', 'success');
            this.dialogRef.close({ action: 'delete', postId: this.postId });
          },
          error: (e) => {
            this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
          },
        });
      }
    });
  }
}
