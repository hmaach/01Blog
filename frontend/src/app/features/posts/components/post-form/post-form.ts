import { Component, Inject, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MediaGrid } from './media-grid/media-grid';
import { Post } from '../../models/post-model';
import { PostApiService } from '../../services/post-api.service';
import { ToastService } from '../../../../core/services/toast.service';
import { UploadedMedia } from '../../models/media-model';
import { BlobService } from '../../../../core/services/blob.service';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatInputModule,
    MatCardModule,
    MediaGrid,
  ],
  templateUrl: './post-form.html',
  styleUrl: './post-form.scss',
})
export class PostForm {
  private postApi = inject(PostApiService);
  private blobService = inject(BlobService);
  private toast = inject(ToastService);

  title = '';
  body = '';
  mediaFiles: UploadedMedia[] = [];
  addedMedias: string[] = [];
  deletedMedias: string[] = [];

  maxTitleChars = 100;
  maxBodyChars = 1000;
  maxMediaCount = 5;

  isLoading: boolean = false;

  constructor(
    private dialogRef: MatDialogRef<PostForm>,
    @Inject(MAT_DIALOG_DATA) public data: { action: string; post?: Post }
  ) {}

  ngOnInit(): void {
    const editedPost: Post | undefined = this.data.post;

    if (editedPost) {
      this.title = editedPost.title;
      this.body = editedPost.body;
      if (editedPost.media) {
        editedPost.media.forEach((media) => {
          this.mediaFiles.push({
            id: media.id,
            url: media.url,
            mediaType: media.mediaType,
            status: 'uploaded',
          });
        });
      }
    }
  }

  onClose(): void {
    if (!this.isLoading) {
      if (this.mediaFiles.length > 0) {
        this.mediaFiles.forEach((media) => {
          this.blobService.deleteMedia(media.id).subscribe();
        });
      }
      this.dialogRef.close();
    }
  }

  handleMediaUpload(event: Event): void {
    const input = event.target as HTMLInputElement;
    const files = input.files;
    if (!files) return;

    const remainingSlots = this.maxMediaCount - this.mediaFiles.length;
    const selected = Array.from(files).slice(0, remainingSlots);

    selected.forEach((file) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        const media: UploadedMedia = {
          id: 'default_id',
          url: reader.result as string,
          status: 'loading',
          file,
        };
        this.mediaFiles.push(media);
        const formData: FormData = new FormData();
        media.file && formData.append('file', media.file);
        this.blobService.uploadPostMedia(formData).subscribe({
          next: (response) => {
            this.addedMedias.push(response.id);
            media.id = response.id;
            media.status = 'uploaded';
          },
          error: (e) => {
            this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
            media.status = 'failed';
          },
        });
      };
      reader.readAsDataURL(file);
    });

    // Reset the input file after handling the files so the same file can be uploaded again.
    input.value = '';
  }
  // TODO:dd
  removeMedia(index: number): void {
    switch (this.data.action) {
      case 'create':
        this.blobService.deleteMedia(this.mediaFiles[index].id).subscribe();
        break;
      case 'edit':
        this.deletedMedias.push(this.mediaFiles[index].id);
        break;
    }
    this.mediaFiles.splice(index, 1);
  }

  get hasInvalidMedia(): boolean {
    return this.mediaFiles.some((m) => m.status === 'loading' || m.status === 'failed');
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  handleCreatePost(): void {
    this.isLoading = true;

    const formData = new FormData();
    formData.append('title', this.title);
    formData.append('body', this.body);

    this.mediaFiles.map((media) => formData.append('medias', media.id));

    this.postApi.createPost(formData).subscribe({
      next: (response) => {
        const newPost: Post = response;
        this.toast.show('Post created successfully', 'success');
        this.dialogRef.close(newPost);
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  handleUpdatePost(): void {
    if (!this.data.post) return;
    this.isLoading = true;

    const formData = new FormData();
    this.data.post?.title !== this.title && formData.append('title', this.title);
    this.data.post?.body !== this.body && formData.append('body', this.body);

    this.deletedMedias.length > 0 &&
      this.deletedMedias.map((media) => formData.append('deletedMedias', media));

    this.addedMedias.length > 0 &&
      this.addedMedias.map((media) => formData.append('addedMedias', media));

    this.postApi.updatePost(this.data.post.id, formData).subscribe({
      next: (response) => {
        const updatedPost: Post = response;
        this.toast.show('Post updated successfully', 'success');
        this.dialogRef.close(updatedPost);
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  handleSubmit(): void {
    if (this.data.action === 'create') {
      this.handleCreatePost();
    } else if (this.data.action === 'edit') {
      this.handleUpdatePost();
    }
  }
}
