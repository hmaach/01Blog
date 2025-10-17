import { Routes } from '@angular/router';
import { Login } from './features/auth/components/login/login';
import { Register } from './features/auth/components/register/register';
import { AuthGuard } from './core/guards/auth.guard';
import { MainLayout } from './layouts/main-layout/main-layout';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { LoginGuard } from './core/guards/login.guard';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    // canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'feed', pathMatch: 'full' },
      {
        path: 'feed',
        loadComponent: () => import('./features/feed/components/feed/feed').then((m) => m.Feed),
      },
      {
        path: 'profile/:username',
        loadComponent: () =>
          import('./features/profile/components/profile/profile').then((m) => m.Profile),
      },
      {
        path: 'profile/me',
        loadComponent: () =>
          import('./features/profile/components/profile/profile').then((m) => m.Profile),
      },
      {
        path: 'post/:id',
        loadComponent: () =>
          import('./features/posts/components/post-detail/post-detail').then((m) => m.PostDetail),
      },
    ],
  },
  {
    path: 'auth',
    component: AuthLayout,
    canActivate: [LoginGuard],
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: Login },
      { path: 'register', component: Register },
    ],
  },
  {
    path: '**',
    loadComponent: () => import('./shared/components/not-found/not-found').then((m) => m.NotFound),
  },
];
