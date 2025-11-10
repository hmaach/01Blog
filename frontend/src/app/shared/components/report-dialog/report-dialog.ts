import { Component, EventEmitter, inject, Inject, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ReportApiService } from '../../../core/services/report-api.service';
import { ReportPayload } from '../../models/report.model';
import { ToastService } from '../../../core/services/toast.service';
import { Confirmation } from '../confirmation/confirmation';

@Component({
  selector: 'app-report-dialog',
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
    MatProgressBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './report-dialog.html',
  styleUrl: './report-dialog.scss',
})
export class ReportDialog {
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<{ category: string; reason: string }>();

  private reportApi = inject(ReportApiService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);

  reportCategory = 'spam';
  reportReason = '';
  reported!: string;
  isLoading: boolean = false;

  constructor(
    private dialogRef: MatDialogRef<ReportDialog>,
    @Inject(MAT_DIALOG_DATA)
    public data: {
      reportedUserId: string;
      reportedPostId: string;
      reportedCommentId: string;
      reportType: string;
      closeDialog?: () => void;
    }
  ) {}

  ngOnInit(): void {
    this.reported = this.data.reportType;
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  handleSubmit(): void {
    const dialogRef = this.dialog.open(Confirmation, {
      data: { message: `Are you sure you want to report this ${this.data.reportType} ?` },
      panelClass: 'post-report-dialog',
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.isLoading = true;

        const payload: ReportPayload = {
          reportedUserId: this.data.reportedUserId,
          reportedPostId: this.data.reportedPostId,
          reportedCommentId: this.data.reportedCommentId,
          reportType: this.data.reportType, // post | user | comment
          category: this.reportCategory,
          reason: this.reportReason,
        };

        this.reportApi.reportPost(payload).subscribe({
          next: () => {
            this.toast.show('Your report has been submitted', 'success');
            this.isLoading = false;
            this.data.closeDialog?.();
            this.closeDialog();
          },
          error: (e) => {
            this.isLoading = false;
          },
        });
      }
    });
  }
}
