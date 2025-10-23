import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-profile-not-found',
  imports: [],
  templateUrl: './profile-not-found.html',
  styleUrl: './profile-not-found.scss',
})
export class ProfileNotFound {
  @Input() username!: string;
}
