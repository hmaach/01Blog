import { Component, Inject, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MediaPreview } from '../../../../../shared/components/media-preview/media-preview';

@Component({
  selector: 'app-media-grid',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule],
  templateUrl: './media-grid.html',
  styleUrl: './media-grid.scss'
})
export class MediaGrid {


  @Input() mediaFiles: { src: string; file: File }[] = [];
  @Output() remove = new EventEmitter<number>();

  constructor(private dialog: MatDialog) { }

  preview(media: string) {
    this.dialog.open(MediaPreview, {
      data: { media },
      panelClass: 'media-preview-dialog'
    });
  }
}
