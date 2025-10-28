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
import { Router } from '@angular/router';

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
  private toast = inject(ToastService);
  private router = inject(Router);

  title = '';
  body = '';
  mediaFiles: { src: string; file: File }[] = [];

  maxTitleChars = 100;
  maxBodyChars = 1000;
  maxMediaCount = 5;

  isLoading: boolean = false;

  constructor(private dialogRef: MatDialogRef<PostForm>) {}

  onClose(): void {
    if (!this.isLoading) {
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
        this.mediaFiles.push({ src: reader.result as string, file });
      };
      reader.readAsDataURL(file);
    });
  }

  removeMedia(index: number): void {
    this.mediaFiles.splice(index, 1);
  }

  handleSubmit(): void {
    const formData = new FormData();

    formData.append('title', this.title);
    formData.append('body', this.body);

    this.mediaFiles.forEach((media) => {
      formData.append('files', media.file);
    });

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

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
