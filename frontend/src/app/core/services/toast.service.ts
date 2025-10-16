import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Toast } from '../../shared/components/toast/toast';

@Injectable({ providedIn: 'root' })
export class ToastService {
  constructor(private snackBar: MatSnackBar) {}

  show(message: string, type: 'success' | 'error' | 'info' | 'warning' = 'info') {
    this.snackBar.openFromComponent(Toast, {
      duration: 3000,
      data: { message, type },
      horizontalPosition: 'right',
      verticalPosition: 'top',
      panelClass: [`toast-${type}`],
    });

    // this.playSound(type);
  }

  private playSound(type: string) {
    const soundMap: Record<string, string> = {
      success: '/assets/sounds/success.mp3',
      error: '/assets/sounds/error.mp3',
      warning: '/assets/sounds/warning.mp3',
      subscribe: '/assets/sounds/subscribe.mp3',
      info: '/assets/sounds/info.mp3',
    };

    const soundPath = soundMap[type] || soundMap['info'];
    const audio = new Audio(soundPath);
    audio.load();
    audio.play().catch((err) => console.warn('Sound playback failed:', err));
  }
}
