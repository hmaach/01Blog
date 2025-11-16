import {
  APP_INITIALIZER,
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { AuthService } from './features/auth/services/auth.service';
import { provideMarkdown } from 'ngx-markdown';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    provideClientHydration(withEventReplay()),
    provideMarkdown(),
    provideHttpClient(withFetch()),
    // {
    //   provide: APP_INITIALIZER,
    //   useFactory: (authService: AuthService) => () => authService.validateToken(),
    //   deps: [AuthService],
    //   multi: true,
    // },
  ],
};
