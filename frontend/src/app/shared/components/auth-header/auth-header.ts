import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Logo } from '../logo/logo';
import { MatIconModule } from "@angular/material/icon";
import { SwitchMode } from "../switch-mode/switch-mode";

@Component({
  selector: 'app-auth-header',
  imports: [
    RouterLink,
    RouterLinkActive,
    MatButtonModule,
    MatToolbarModule,
    Logo,
    MatSlideToggleModule,
    MatIconModule,
    SwitchMode
],
  templateUrl: './auth-header.html',
  styleUrl: './auth-header.scss',
})
export class AuthHeader {
}
