import { Component } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PostForm } from '../../../features/posts/components/post-form/post-form';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-create-post-button',
  imports: [
    CommonModule,
    MatMenuModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
  ],
  templateUrl: './create-post-button.html',
  styleUrl: './create-post-button.scss'
})
export class CreatePostButton {

  constructor(private dialog: MatDialog) { }

  openCreatePostDialog(): void {
    this.dialog.open(PostForm, {
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'post-form-dialog'
    });
  }

}
