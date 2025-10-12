import { NgModule } from '@angular/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Toast } from './components/toast/toast';

@NgModule({
  imports: [MatSnackBarModule, Toast],
//   declarations: [Toast],
  exports: [Toast],
})
export class SharedModule {}
