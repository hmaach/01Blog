import { Component } from '@angular/core';
import { ThemeService } from '../../../core/services/theme.service';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-switch-mode',
  imports: [MatSlideToggleModule],
  templateUrl: './switch-mode.html',
  styleUrl: './switch-mode.scss',
})
export class SwitchMode {
  constructor(public themeService: ThemeService) {}
}
