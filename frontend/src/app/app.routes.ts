import { Routes } from '@angular/router';
import { Login } from './features/auth/components/login/login';
import { Register } from './features/auth/components/register/register';
import { AuthGuard } from './core/guards/auth.guard';
import { MainLayout } from './layouts/main-layout/main-layout';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { LoginGuard } from './core/guards/login.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { AdminMain } from './features/admin/components/admin-main/admin-main';
import { AdminLayout } from './layouts/admin-layout/admin-layout';
import { AdminUsers } from './features/admin/components/admin-users/admin-users';
import { AdminReports } from './features/admin/components/admin-reports/admin-reports';

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
        path: 'explore',
        loadComponent: () =>
          import('./features/feed/components/explore-feed/explore-feed').then((m) => m.ExploreFeed),
      },
      {
        path: 'profile/:username',
        loadComponent: () =>
          import('./features/profile/components/profile/profile').then((m) => m.Profile),
      },
      {
        path: 'settings',
        loadComponent: () =>
          import('./features/settings/components/settings/settings').then((m) => m.Settings),
      },
    ],
  },
  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [AdminGuard],
    children: [
      { path: '', component: AdminMain },
      { path: 'users', component: AdminUsers },
      { path: 'reports', component: AdminReports },
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
