import { CommonModule } from '@angular/common';
import { Component, computed, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { mockUsers } from '../../../../shared/lib/mock-data';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-admin-users',
  imports: [CommonModule, MatCardModule, MatIconModule, MatButtonModule, RouterLink],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.scss',
})
export class AdminUsers {
  searchQuery = signal('');
  actionMessage = signal('');

  users = signal(
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
    return this.users().filter(
      (user) => user.username.toLowerCase().includes(q) || user.name.toLowerCase().includes(q)
    );
  });

  // --- Actions ---
  handleBanUser(userId: string) {
    this.users.update((list) =>
      list.map((u) => (u.id === userId ? { ...u, isBanned: !u.isBanned } : u))
    );

    const user = this.users().find((u) => u.id === userId);
    const newStatus = user?.isBanned ? 'banned' : 'unbanned';

    this.showMessage(`User ${user?.name} has been ${newStatus}`);
  }

  handleDeleteUser(userId: string) {
    const user = this.users().find((u) => u.id === userId);
    if (!user) return;

    if (confirm(`Are you sure you want to delete ${user.name}? This action cannot be undone.`)) {
      this.users.update((list) => list.filter((u) => u.id !== userId));
      this.showMessage(`User ${user.name} has been deleted`);
    }
  }

  handleChangeRole(userId: string) {
    this.users.update((list) =>
      list.map((u) => (u.id === userId ? { ...u, role: u.role === 'user' ? 'admin' : 'user' } : u))
    );

    const user = this.users().find((u) => u.id === userId);
    const newRole = user?.role === 'user' ? 'admin' : 'user';

    this.showMessage(`${user?.name} is now an ${newRole}`);
  }

  private showMessage(text: string) {
    this.actionMessage.set(text);
    setTimeout(() => this.actionMessage.set(''), 3000);
  }
}
