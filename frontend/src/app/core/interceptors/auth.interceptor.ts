import { inject, Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../../features/auth/services/auth.service';
import { StorageService } from '../services/storage.service';
import { ToastService } from '../services/toast.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private storageService = inject(StorageService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private toast = inject(ToastService);

  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        console.log(error);
        if (error.status === 401 && error.error.error === 'UNAUTHORIZED_BANNED') {
          console.log('banned');
          this.storageService.clear();
          this.toast.show('Logged out', 'info');
          this.router.navigate(['/auth/login']);
        }
        throw error;
      })
    );
  }
}
