import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  toggleDarkMode(): void {
    document.documentElement.classList.toggle('dark-theme');
  }
}
