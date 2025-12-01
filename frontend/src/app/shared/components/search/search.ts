import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { UserResponse } from '../../../features/profile/models/user-response.model';
import { AdminApiService } from '../../../features/admin/services/admin-api.service';
import { BlobService } from '../../../core/services/blob.service';
import { ToastService } from '../../../core/services/toast.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { ProfileDialog } from '../../../features/profile/components/profile-dialog/profile-dialog';
import { ProfileApiService } from '../../../features/profile/services/profile-api.service';
import { StorageService } from '../../../core/services/storage.service';

@Component({
  selector: 'app-search',
  imports: [CommonModule, MatCardModule, MatIconModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './search.html',
  styleUrl: './search.scss',
})
export class Search {
  users: UserResponse[] = [];

  private apiService = inject(ProfileApiService);
  private blobService = inject(BlobService);
  private storageService = inject(StorageService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);

  isLoading: boolean = true;
  noMoreUsers = false;
  searchControl = new FormControl('');
  query: string | null = null;
  currUserId: string | undefined = this.storageService.getCurrentUserInfo()?.id;

  private limit: number = 5;

  constructor(private dialogRef: MatDialogRef<Search>) {}

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((q) => {
        if (!q || q?.trim().length === 0) {
          this.query = null;
          this.users = [];
          return;
        }
        this.query = q.trim();
        this.loadUsers();
      });
  }

  private loadUsers() {
    this.apiService.fetchUsers(this.query, null, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0 || !Array.isArray(response)) {
          this.users = [];
          this.noMoreUsers = true;
          this.isLoading = false;
          return;
        }

        this.users = [];
        this.users = response;

        this.users.forEach((u) => {
          if (u.avatarUrl) {
            this.blobService.loadBlob(u.avatarUrl).subscribe({
              next: (url) => (u.avatarUrl = url),
            });
          }
        });

        this.isLoading = false;
      },
      error: (e) => {
        this.toast.show(e?.error?.message || 'Unknown Server Error', 'error');
        this.isLoading = false;
      },
    });
  }

  openUserCardDialog(username: string | undefined) {
    if (!username) return;
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
