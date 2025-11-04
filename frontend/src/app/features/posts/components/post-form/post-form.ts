import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatDialogRef } from '@angular/material/dialog';
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

  maxTitleChars = 100;
  maxBodyChars = 1000;
  maxMediaCount = 5;

  isLoading: boolean = false;

  constructor(private dialogRef: MatDialogRef<PostForm>) {}

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
        formData.append('file', media.file);
        this.blobService.uploadPostMedia(formData).subscribe({
          next: (response) => {
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

  removeMedia(index: number): void {
    this.blobService.deleteMedia(this.mediaFiles[index].id).subscribe();
    this.mediaFiles.splice(index, 1);
  }

  handleSubmit(): void {
    const formData = new FormData();

    formData.append('title', this.title);
    formData.append('body', this.body);

    this.mediaFiles.map((media) => formData.append('medias', media.id));

    // this.mediaFiles.forEach((media) => {
    //   media.id && formData.append('medias', media.id);
    // });

    this.isLoading = true;

    this.postApi.createPost(formData).subscribe({
      next: (response) => {
        const newPost: Post = response;
        this.dialogRef.close(newPost);
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  get hasInvalidMedia(): boolean {
    return this.mediaFiles.some((m) => m.status === 'loading' || m.status === 'failed');
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
