import { CommonModule } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { mockUsers } from '../../../../shared/lib/mock-data';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { ToastService } from '../../../../core/services/toast.service';
import { AdminApiService } from '../../services/admin-api.service';
import { MatDialog } from '@angular/material/dialog';
import { User } from '../../../../core/models/user.model';
import { UserResponse } from '../../../profile/models/user-response.model';
import { formatDate } from '../../../../shared/lib/date';
import { ProfileDialog } from '../../../profile/components/profile-dialog/profile-dialog';
import { Spinner } from '../../../../shared/components/spinner/spinner';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { BlobService } from '../../../../core/services/blob.service';

@Component({
  selector: 'app-admin-users',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    Spinner,
    ReactiveFormsModule,
  ],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.scss',
})
export class AdminUsers {
  users: UserResponse[] = [];

  private apiService = inject(AdminApiService);
  private blobService = inject(BlobService);
  private toast = inject(ToastService);
  private dialog = inject(MatDialog);
  formatDate = formatDate;

  isLoading: boolean = true;
  noMoreUsers = false;

  private query: string | null = null;
  private limit: number = 9;
  private scrollDistance = 0.8;
  private throttle: number = 300;
  private isThrottled = false;
  private lastUserTime: string | null = null;

  searchQuery = signal('');

  searchControl = new FormControl('');

  ngOnInit(): void {
    this.loadUsers('load');

    this.searchControl.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((q) => {
        this.query = q;
        this.loadUsers('search');
      });
  }

  private loadUsers(actionType: 'search' | 'load' | 'loadMore') {
    this.apiService.fetchUsers(this.query, this.lastUserTime, this.limit).subscribe({
      next: (response) => {
        if (response.length === 0 || !Array.isArray(response)) {
          if (actionType === 'search') this.users = [];
          this.noMoreUsers = true;
          this.isLoading = false;
          return;
        }

        this.lastUserTime = response.at(-1)?.createdAt ?? null;
        if (actionType === 'search') {
          this.users = [];
          this.users = response;
        } else this.users.push(...response);

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

  userss = signal(
    mockUsers.map((u) => ({
      id: u.id,
      username: u.username,
      name: u.name,
      avatar: u.avatar,
      totalPosts: u.stats.totalPosts,
      subscribers: u.stats.subscribers,
      joinDate: new Date(u.joinDate).toLocaleDateString(),
      role: u.role,
      isBanned: false,
    }))
  );

  filteredUsers = computed(() => {
    const q = this.searchQuery().toLowerCase();
    return this.userss().filter(
      (user) => user.username.toLowerCase().includes(q) || user.name.toLowerCase().includes(q)
    );
  });

  // --- Actions ---
  handleBanUser(userId: string) {
    this.userss.update((list) =>
      list.map((u) => (u.id === userId ? { ...u, isBanned: !u.isBanned } : u))
    );

    const user = this.userss().find((u) => u.id === userId);
    const newStatus = user?.isBanned ? 'banned' : 'unbanned';
  }

  handleDeleteUser(userId: string) {
    const user = this.userss().find((u) => u.id === userId);
    if (!user) return;

    if (confirm(`Are you sure you want to delete ${user.name}? This action cannot be undone.`)) {
      this.userss.update((list) => list.filter((u) => u.id !== userId));
    }
  }

  handleChangeRole(userId: string) {
    this.userss.update((list) =>
      list.map((u) => (u.id === userId ? { ...u, role: u.role === 'user' ? 'admin' : 'user' } : u))
    );

    const user = this.userss().find((u) => u.id === userId);
    const newRole = user?.role === 'user' ? 'admin' : 'user';
  }

  openUserCardDialog(username: string | undefined) {
    if (!username) return;
    this.dialog.open(ProfileDialog, {
      data: { username },
      panelClass: 'user-card-dialog',
    });
  }
}
