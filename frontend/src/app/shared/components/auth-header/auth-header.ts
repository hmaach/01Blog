import { Component } from '@angular/core';
import { ThemeService } from '../../../core/services/theme.service';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-auth-header',
  imports: [RouterLink, RouterLinkActive, MatButtonModule],
  templateUrl: './auth-header.html',
  styleUrl: './auth-header.scss',
})
export class AuthHeader {
  constructor(public themeService: ThemeService) {}
}
