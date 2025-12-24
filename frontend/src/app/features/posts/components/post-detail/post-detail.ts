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
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { Observable } from 'rxjs';
import { UUID } from 'crypto';
import { AdminApiService } from '../../../admin/services/admin-api.service';

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
    Spinner,
  ],
  templateUrl: './post-detail.html',
  styleUrls: ['./post-detail.scss'],
})
export class PostDetail {
  @Output() close = new EventEmitter<void>();

  private storageService = inject(StorageService);
  private postApi = inject(PostApiService);
  private commentApi = inject(CommentApiService);
  private adminService = inject(AdminApiService);
  private toast = inject(ToastService);
  private blobService = inject(BlobService);

  comments: Comment[] = [];
  postId!: string;
  post!: Post;
  menuOpen = false;
  isAdmin: boolean = this.storageService.isAdmin();
  formatDate = formatDate;
  isLoading: boolean = true;
  notFound: boolean = false;
  isMediaLoading: boolean = true;
  isUpdated: boolean = false;
  private isBrowser: boolean;

  private isThrottled: boolean = false;
  private throttle: number = 300;
  private commentsLimit: number = 10;
  private lastCommentTime: string | null = null;
  commentText = '';
  isCommentsLoading: boolean = true;
  isLoadingMoreComments: boolean = false;
  noMoreComments: boolean = false;
  private observer!: IntersectionObserver;

  constructor(
    private dialogRef: MatDialogRef<PostDetail>,
    @Inject(MAT_DIALOG_DATA) public data: { post: Post; postId: string },
    private dialog: MatDialog
  ) {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

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

    if (this.postId) {
      this.loadPostDetail();
      this.loadComments();
    }
  }

  ngAfterViewInit() {
    if (!this.isBrowser) {
      return;
    }
    this.observer = new IntersectionObserver((entries, observer) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting && !this.isLoading && !this.isCommentsLoading) {
          if (this.isThrottled) return;

          this.isThrottled = true;

          setTimeout(() => {
            this.isThrottled = false;

            if (!this.noMoreComments && !this.isLoadingMoreComments) {
              this.isLoadingMoreComments = true;
              this.loadComments();
            }
          }, this.throttle);
        }
      });
    });

    const target = document.getElementById('scroll-trigger');
    if (target) {
      this.observer.observe(target);
    }
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

  togglePostStatus() {
    if (!this.postId || !this.isAdmin) return;

    const dialogRef = this.dialog.open(Confirmation, {
      data: {
        message: `Are you sure you want to ${
          this.post.status === 'PUBLISHED' ? 'Hide' : 'Unhide'
        } this post ?`,
      },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        if (confirmed && this.post?.id) {
          const newStatus = this.post.status === 'PUBLISHED' ? 'HIDDEN' : 'PUBLISHED';
          this.adminService.changePostStatus(this.post.id, newStatus).subscribe({
            next: () => {
              this.toast.show("Post's status changed seccufully!", 'success');
              this.dialogRef.close({ action: 'changeStatus', postId: this.post?.id, newStatus });
            },
            error: (e) => {
              this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
            },
          });
        }
      }
    });
  }

  openUserCardDialog(username: string) {
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  openPostReportDialog(): void {
    if (!this.post) return;

    this.dialog.open(ReportDialog, {
      data: {
        reportedType: 'POST',
        reportedUserId: this.post.author.id,
        reportedPostId: this.postId,
      },
      maxHeight: '90vh',
      panelClass: 'post-report-dialog',
    });
  }

  openCommentReportDialog(commentId: string, authorId: string): void {
    if (!commentId) return;

    this.dialog.open(ReportDialog, {
      data: {
        reportedType: 'COMMENT',
        reportedUserId: authorId,
        reportedCommentId: commentId,
      },
      maxHeight: '90vh',
      panelClass: 'report-dialog',
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
        this.isUpdated = true;
      }
    });
  }

  //TODO: update the media and the comments count in the posts list
  handleCreateComment(): void {
    this.commentApi.createComment(this.postId, this.commentText).subscribe({
      next: (newComment: Comment) => {
        if (newComment) {
          const author: Author = {
            id: this.storageService.getCurrentUserInfo()?.id || '',
            name: this.storageService.getCurrentUserInfo()?.name || '',
            username: this.storageService.getCurrentUserInfo()?.username || '',
            avatarUrl: this.storageService.getUserAvatarUrl() || null,
          };

          if (author.avatarUrl) {
            this.blobService.loadBlob(author.avatarUrl).subscribe({
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
    if (this.isUpdated) {
      this.dialogRef.close({ action: 'update', post: this.post });
    } else {
      this.dialogRef.close();
    }
  }

  private loadPostDetail() {
    const postId: string = this.data.postId ? this.data.postId : this.data.post.id;

    if (!postId) return;
    this.postApi.fetchPostDetail(postId).subscribe({
      next: (post: Post) => {
        // just to not update the user avatar because is already fetched
        const avatarUrl = this.post?.author.avatarUrl;
        this.post = post;
        if (avatarUrl) {
          this.post.author.avatarUrl = avatarUrl;
        } else if (this.post.author.avatarUrl) {
          this.blobService.loadBlob(this.post.author.avatarUrl).subscribe({
            next: (url) => {
              this.post.author.avatarUrl = url;
            },
          });
        }

        this.isLoading = false;
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
        this.notFound = e.status === 404;
        if (e.status !== 404) this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
        this.isMediaLoading = false;
      },
    });
  }

  private loadComments() {
    if (!this.postId || this.notFound) return;
    this.commentApi.fetchComments(this.postId, this.lastCommentTime, this.commentsLimit).subscribe({
      next: (comments) => {
        if (!comments || comments.length === 0) {
          this.noMoreComments = true;
          this.isCommentsLoading = false;
          this.isLoadingMoreComments = false;
          return;
        }

        if (comments.length > 0) {
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
          this.isLoadingMoreComments = false;
        }
      },
      error: (e) => {
        this.notFound = e.status === 404;
        if (e.status !== 404) this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isCommentsLoading = false;
      },
    });
  }

  handleDeleteComment(commentId: string) {
    if (!commentId) return;
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: 'Are you sure you want to delete?' },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        const response: Observable<void> = this.isAdmin
          ? this.adminService.deleteComment(commentId)
          : this.commentApi.deleteComment(commentId);

        response.subscribe({
          next: () => {
            this.comments = this.comments.filter((c) => c.id !== commentId);
            this.post.commentsCount -= 1;
            this.toast.show('Comment deleted successfully!', 'success');
          },
          error: (e) => {
            this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
            this.isLoading = false;
          },
        });
      }
    });
  }

  handleDeletePost() {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: 'Are you sure you want to delete?' },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        const response: Observable<void> = this.isAdmin
          ? this.adminService.deletePost(this.postId)
          : this.postApi.deletePost(this.postId);
        response.subscribe({
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
