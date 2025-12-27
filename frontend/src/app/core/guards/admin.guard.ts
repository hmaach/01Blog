import { inject, Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { StorageService } from '../services/storage.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  private storageService = inject(StorageService);
  private router = inject(Router);

  private isBrowser: boolean;
  constructor() {
    this.isBrowser = typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  canActivate(): boolean {
    // if (!this.isBrowser) {
    //   return false;
    // }

    const role = this.storageService.getUserRole();

    if (role === 'ADMIN') {
      // console.log("is admin");
      return true;
    }
    // console.log("is not admin");

    this.router.navigate(['/feed']);
    return false;
  }
}
