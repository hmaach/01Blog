import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatBadgeModule } from '@angular/material/badge';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-settings',
  imports: [
    CommonModule,
    FormsModule,
    MatTabsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatBadgeModule,
    MatCardModule,
  ],
  standalone: true,
  templateUrl: './settings.html',
  styleUrl: './settings.scss',
})
export class Settings {
  activeTabIndex = 0;
  activeTab = 'profile';

  avatar = '/placeholder.svg';
  displayName = 'John Doe';
  email = 'john@example.com';
  emailVerified = false;
  username = 'johndoe';
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';
  readmeFile: File | null = null;
  readmePreview = '';
  successMessage = '';

  setTab(tab: string) {
    this.activeTab = tab;
  }

  onTabChange(index: number) {
    this.activeTabIndex = index;
    this.activeTab = ['profile', 'readme', 'password', 'account'][index];
  }

  handleAvatarChange(event: any) {
    const file = event.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onloadend = () => {
      this.avatar = reader.result as string;
    };
    reader.readAsDataURL(file);
  }

  handleReadmeUpload(event: any) {
    const file = event.target.files?.[0];
    if (!file || !file.name.endsWith('.md')) return;

    this.readmeFile = file;

    const reader = new FileReader();
    reader.onloadend = () => {
      this.readmePreview = reader.result as string;
    };
    reader.readAsText(file);
  }

  handleSaveProfile() {
    this.successMessage = 'Profile updated successfully!';
    setTimeout(() => (this.successMessage = ''), 3000);
  }

  handleChangePassword() {
    if (this.newPassword === this.confirmPassword && this.newPassword.length >= 6) {
      this.successMessage = 'Password changed successfully!';
      this.currentPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
      setTimeout(() => (this.successMessage = ''), 3000);
    }
  }

  handleDeleteAccount() {
    if (confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
      this.successMessage = 'Account deleted successfully!';
    }
  }
}
