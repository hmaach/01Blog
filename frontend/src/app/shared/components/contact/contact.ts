import { Component, inject } from '@angular/core';
import {
  MatBottomSheet,
  MatBottomSheetModule,
  MatBottomSheetRef,
} from '@angular/material/bottom-sheet';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-contact',
  imports: [MatButtonModule, MatBottomSheetModule],
  templateUrl: './contact.html',
  styleUrl: './contact.scss',
})
export class Contact {
  private _bottomSheetRef = inject<MatBottomSheetRef<Contact>>(MatBottomSheetRef);

  openLink(event: MouseEvent): void {
    this._bottomSheetRef.dismiss();
    event.preventDefault();
  }
}
