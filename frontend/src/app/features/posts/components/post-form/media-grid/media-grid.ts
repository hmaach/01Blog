import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDialog } from '@angular/material/dialog';
import { MediaPreview } from '../../../../../shared/components/media-preview/media-preview';
import { UploadedMedia } from '../../../models/media-model';

@Component({
  selector: 'app-media-grid',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatProgressBarModule],
  templateUrl: './media-grid.html',
  styleUrl: './media-grid.scss',
})
export class MediaGrid {
  @Input() isLoading!: boolean;

  @Input() mediaFiles: UploadedMedia[] = [];
  @Output() remove = new EventEmitter<number>();

  constructor(private dialog: MatDialog) {}

  preview(media: string) {
    if (!this.isLoading) {
      this.dialog.open(MediaPreview, {
        data: { media },
        panelClass: 'media-preview-dialog',
      });
    }
  }
}
