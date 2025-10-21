import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import {  MatDialogRef } from '@angular/material/dialog';
import { MediaGrid } from './media-grid/media-grid';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MediaGrid,
  ],
  templateUrl: './post-form.html',
  styleUrl: './post-form.scss'
})
export class PostForm {

  constructor(private dialogRef: MatDialogRef<PostForm>) {}

  title = '';
  body = '';
  mediaFiles: { src: string; file: File }[] = [];

  maxTitleChars = 100;
  maxBodyChars = 1000;
  maxMediaCount = 5;

  onClose(): void {
    this.dialogRef.close();
  }

  handleMediaUpload(event: Event): void {
    const input = event.target as HTMLInputElement;
    const files = input.files;
    if (!files) return;

    const remainingSlots = this.maxMediaCount - this.mediaFiles.length;
    const selected = Array.from(files).slice(0, remainingSlots);

    selected.forEach(file => {
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
    console.log('Post created:', {
      title: this.title,
      body: this.body,
      media: this.mediaFiles,
    });
    this.onClose();
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
