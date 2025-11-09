import { Component, EventEmitter, inject, Inject, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ReportApiService } from '../../services/report-api.service';
import { ReportPayload } from '../../models/report-post.model';

@Component({
  selector: 'app-report-post',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
  ],
  templateUrl: './report-post.html',
  styleUrl: './report-post.scss',
})
export class ReportPost {
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<{ category: string; reason: string }>();

  private reportApi = inject(ReportApiService);

  reportCategory = 'spam';
  reportReason = '';
  reported!: string;

  constructor(
    private dialogRef: MatDialogRef<ReportPost>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      reported: string;
      userId: string;
      postId: string | undefined;
      commentId: string | undefined;
    }
  ) {}

  ngOnInit(): void {
    this.reported = this.data.reported;
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  handleSubmit(): void {
    const payload: ReportPayload = {
      userId: this.data.userId,
      postId: this.data.postId,
      reported: this.data.reported,
      category: this.reportCategory,
      reason: this.reportReason,
    };

    console.log('Report submitted:', payload);
    // this.reportApi.reportPost(payload).subscribe();
    this.closeDialog();
  }
}
