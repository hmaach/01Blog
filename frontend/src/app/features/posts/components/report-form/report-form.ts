import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-report-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule
  ],
  templateUrl: './report-form.html',
  styleUrl: './report-form.scss'
})
export class ReportForm {
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<{ category: string; reason: string }>();

  postId!: string;
  reportCategory = 'spam';
  reportReason = '';

  constructor(
    private dialogRef: MatDialogRef<ReportForm>,
    @Inject(MAT_DIALOG_DATA) public data: { postId: string }
  ) { }

  ngOnInit(): void {
    this.postId = this.data.postId;
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  handleSubmit(): void {
    console.log('Report submitted:', { postId: this.postId, category: this.reportCategory, reason: this.reportReason });
    this.closeDialog()
  }

}
