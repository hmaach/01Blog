import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthHeader } from '../../shared/components/auth-header/auth-header';

@Component({
  selector: 'app-auth-layout',
  imports: [RouterOutlet, AuthHeader],
  templateUrl: './auth-layout.html',
  styleUrl: './auth-layout.scss',
})
export class AuthLayout {}
