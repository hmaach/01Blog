import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthHeader } from '../../shared/components/auth-header/auth-header';
import { MatCard } from '@angular/material/card';
import { MainLogo } from '../../shared/components/logo/main-logo/main-logo';

@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet, AuthHeader, MatCard, MainLogo],
  templateUrl: './auth-layout.html',
  styleUrl: './auth-layout.scss',
})
export class AuthLayout {}
