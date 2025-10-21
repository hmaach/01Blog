import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-media-preview',
  imports: [CommonModule, MatIconModule, MatButtonModule],
  templateUrl: './media-preview.html',
  styleUrl: './media-preview.scss'
})
export class MediaPreview {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { media: string }) { }
  isImage() { return this.data.media.startsWith('data:image'); }
  isVideo() { return this.data.media.startsWith('data:video'); }
}
