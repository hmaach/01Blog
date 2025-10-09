import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { ThemeService } from '../../../../core/services/theme.service';

@Component({
  selector: 'app-header',
  standalone: true, // standalone since you used 'imports' array
  imports: [RouterLink, RouterLinkActive, MatButtonModule],
  templateUrl: './header.html',
  styleUrls: ['./header.scss'],
})
export class Header {
  constructor(public themeService: ThemeService) {}

  logout() {
    // TODO: implement logout logic
  }
}
