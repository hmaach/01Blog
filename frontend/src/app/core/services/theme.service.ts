import { Injectable, signal, effect, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private platformId = inject(PLATFORM_ID);
  private isBrowser = isPlatformBrowser(this.platformId);

  isDarkMode = signal(false);

  constructor() {
    if (this.isBrowser) {
      const savedTheme = localStorage.getItem('theme');
      const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

      this.isDarkMode.set(savedTheme === 'dark' || (!savedTheme && prefersDark));

      effect(() => {
        this.applyTheme(this.isDarkMode());
      });
    }
  }

  toggleDarkMode() {
    this.isDarkMode.update((value) => !value);
  }

  private applyTheme(isDark: boolean) {
    if (!this.isBrowser) return;

    const htmlElement = document.documentElement;

    if (isDark) {
      htmlElement.classList.add('dark-theme', 'dark');
      localStorage.setItem('theme', 'dark');
    } else {
      htmlElement.classList.remove('dark-theme', 'dark');
      localStorage.setItem('theme', 'light');
    }
  }
}
