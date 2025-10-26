import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-media-preview',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule],
  templateUrl: './media-preview.html',
  styleUrls: ['./media-preview.scss'],
})
export class MediaPreview {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { media: string }) {}

  ngOnInit() {
    console.log('Media preview source:', this.data.media);
  }

  isImage(): boolean {
    const media = this.data.media;
    return (
      media.startsWith('data:image') || // base64 image
      media.startsWith('blob:') || // blob URL
      /\.(png|jpe?g|gif|webp|bmp)$/i.test(media) // URL ending with image extension
    );
  }

  isVideo(): boolean {
    const media = this.data.media;
    return (
      media.startsWith('data:video') ||
      (media.startsWith('blob:') && media.includes('video')) ||
      /\.mp4$/i.test(media)
    );
  }
}
