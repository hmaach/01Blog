import { Routes } from '@angular/router';
import { MainLayout } from './features/layouts/main-layout/main-layout';
import { AuthLayout } from './features/layouts/auth-layout/auth-layout';
import { AdminLayout } from './features/layouts/admin-layout/admin-layout';
import { Login } from './features/auth/components/login/login';
import { Register } from './features/auth/components/register/register';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    canActivate: [AuthGuard],
    // children: [
    //   {
    //     path: '',
    //     redirectTo: 'feed',
    //     pathMatch: 'full',
    //   },
    //   {
    //     path: 'feed',
    //     loadComponent: () => import('./features/feed/feed').then((m) => m.FeedComponent),
    //   },
    //   {
    //     path: 'profile/:id',
    //     loadComponent: () => import('./features/profile/profile').then((m) => m.ProfileComponent),
    //   },
    //   {
    //     path: 'post/:id',
    //     loadComponent: () =>
    //       import('./features/post/post-detail').then((m) => m.PostDetailComponent),
    //   },
    // ],
  },
  {
    path: 'auth',
    component: AuthLayout,
    children: [
      { path: 'login', component: Login },
      { path: 'register', component: Register },
    ],
  },
  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [AuthGuard, AdminGuard],
    // children: [
    //   {
    //     path: 'dashboard',
    //     loadComponent: () =>
    //       import('./features/admin/dashboard/dashboard').then((m) => m.AdminDashboardComponent),
    //   },
    //   {
    //     path: 'users',
    //     loadComponent: () =>
    //       import('./features/admin/users/users').then((m) => m.AdminUsersComponent),
    //   },
    //   {
    //     path: 'posts',
    //     loadComponent: () =>
    //       import('./features/admin/posts/posts').then((m) => m.AdminPostsComponent),
    //   },
    //   {
    //     path: 'reports',
    //     loadComponent: () =>
    //       import('./features/admin/reports/reports').then((m) => m.AdminReportsComponent),
    //   },
    // ],
  },
  {
    path: '**',
    loadComponent: () =>
      import('./features/shared/components/not-found/not-found').then((m) => m.NotFound),
  },
];
