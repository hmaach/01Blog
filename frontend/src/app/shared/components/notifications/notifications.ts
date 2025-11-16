import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { mockCurrentUser, mockNotifications } from '../../lib/mock-data';
import { formatDate } from '../../lib/date';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.html',
  styleUrl: './notifications.scss',
})
export class Notifications {
  currentUser = mockCurrentUser;
  notifications = mockNotifications;
  formatDate = formatDate;
}
