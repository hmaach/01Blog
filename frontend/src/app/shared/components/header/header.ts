import { Component, inject } from '@angular/core';
import { HeaderLogo } from '../logo/header-logo/header-logo';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { SwitchMode } from '../switch-mode/switch-mode';
import { AuthService } from '../../../features/auth/services/auth.service';
import { MatBadgeModule } from '@angular/material/badge';
import { MatDialog } from '@angular/material/dialog';
import { Notifications } from '../notifications/notifications';
import { StorageService } from '../../../core/services/storage.service';
import { BlobService } from '../../../core/services/blob.service';
import { CurrentUserInfo } from '../../../core/models/user.model';
import { ThemeService } from '../../../core/services/theme.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    HeaderLogo,
    SwitchMode,
    RouterLink,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    RouterLinkActive,
    MatBadgeModule,
  ],
  templateUrl: './header.html',
  styleUrls: ['./header.scss'],
})
export class Header {
  private authService = inject(AuthService);
  private blobService = inject(BlobService);
  private storageService = inject(StorageService);

  isAdmin: boolean = this.storageService.getUserRole() === 'ADMIN';
  user: CurrentUserInfo | null = this.storageService.getCurrentUserInfo();

  avatarUrl?: string;
  constructor(private dialog: MatDialog, public themeService: ThemeService) { }

  ngOnInit() {
    const avatar: string | null = this.storageService.getUserAvatarUrl();
    if (avatar) {
      this.blobService.loadBlob(avatar).subscribe({
        next: (url) => (this.avatarUrl = url),
      });
    }
  }

  openNotificationsDialog() {
    this.dialog.open(Notifications, {
      panelClass: 'media-preview-dialog',
    });
  }

  logout() {
    this.authService.logout();
  }
}
