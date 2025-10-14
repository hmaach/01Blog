import { Component, inject } from '@angular/core';
import { Logo } from '../logo/logo';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { SwitchMode } from '../switch-mode/switch-mode';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    Logo,
    SwitchMode,
    RouterLink,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatToolbarModule,
    RouterLinkActive,
  ],
  templateUrl: './header.html',
  styleUrls: ['./header.scss'],
})
export class Header {
  private authService = inject(AuthService);

  logout() {
    this.authService.logout();
  }
}
