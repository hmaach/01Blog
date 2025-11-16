import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { mockUsers } from '../../../../shared/lib/mock-data';

@Component({
  selector: 'app-admin-users',
  imports: [CommonModule, MatCardModule],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.scss',
})
export class AdminUsers {
  users = [...mockUsers];

  handleBanUser(userId: string) {
    console.log('Ban user:', userId);
  }

  handleDeleteUser(userId: string) {
    this.users = this.users.filter((u) => u.id !== userId);
    console.log('Delete user:', userId);
  }

  handleChangeRole(userId: string, newRole: 'admin' | 'user') {
    this.users = this.users.map((u) => (u.id === userId ? { ...u, role: newRole } : u));
    console.log('Change role:', userId, newRole);
  }
}
