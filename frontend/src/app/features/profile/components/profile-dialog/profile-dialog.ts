import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProfileCard } from "../profile-card/profile-card";

@Component({
  selector: 'app-profile-dialog',
  imports: [ProfileCard],
  templateUrl: './profile-dialog.html',
  styleUrl: './profile-dialog.scss',
})
export class ProfileDialog {
  username?: string;
  constructor(@Inject(MAT_DIALOG_DATA) public data: { username: string }) {}

  ngOnInit() {
    this.username = this.data.username;
  }
}
