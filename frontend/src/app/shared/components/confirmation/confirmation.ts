import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-confirmation',
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './confirmation.html',
  styleUrl: './confirmation.scss',
})
export class Confirmation {
  message!: string;

  constructor(
    private dialogRef: MatDialogRef<Confirmation>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string }
  ) {}

  ngOnInit(): void {
    this.message = this.data.message;
  }

  handleConfirm(): void {
    this.dialogRef.close(true);
  }

  handleCancel(): void {
    this.dialogRef.close(false);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
