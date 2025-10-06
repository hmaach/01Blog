```text
src/
├── app/
│   ├── core/                          # Singleton services (loaded once)
│   │   ├── guards/
│   │   │   ├── auth.guard.ts
│   │   │   └── admin.guard.ts
│   │   ├── interceptors/
│   │   │   ├── auth.interceptor.ts
│   │   │   ├── error.interceptor.ts
│   │   │   └── loading.interceptor.ts
│   │   ├── services/
│   │   │   ├── auth.service.ts
│   │   │   ├── token.service.ts
│   │   │   └── notification.service.ts
│   │   └── models/
│   │       ├── user.model.ts
│   │       ├── api-response.model.ts
│   │       └── error-response.model.ts
│   │
│   ├── features/                      # Feature modules (lazy loaded)
│   │   ├── auth/
│   │   │   ├── components/
│   │   │   │   ├── login/
│   │   │   │   │   ├── login.component.ts
│   │   │   │   │   ├── login.component.html
│   │   │   │   │   └── login.component.scss
│   │   │   │   └── register/
│   │   │   │       ├── register.component.ts
│   │   │   │       ├── register.component.html
│   │   │   │       └── register.component.scss
│   │   │   ├── services/
│   │   │   │   └── auth-api.service.ts
│   │   │   └── auth.module.ts
│   │   │
│   │   ├── posts/
│   │   │   ├── components/
│   │   │   │   ├── post-list/
│   │   │   │   │   ├── post-list.component.ts
│   │   │   │   │   ├── post-list.component.html
│   │   │   │   │   └── post-list.component.scss
│   │   │   │   ├── post-card/
│   │   │   │   │   ├── post-card.component.ts
│   │   │   │   │   ├── post-card.component.html
│   │   │   │   │   └── post-card.component.scss
│   │   │   │   ├── post-detail/
│   │   │   │   │   ├── post-detail.component.ts
│   │   │   │   │   ├── post-detail.component.html
│   │   │   │   │   └── post-detail.component.scss
│   │   │   │   ├── post-form/
│   │   │   │   │   ├── post-form.component.ts
│   │   │   │   │   ├── post-form.component.html
│   │   │   │   │   └── post-form.component.scss
│   │   │   │   ├── comment-list/
│   │   │   │   │   ├── comment-list.component.ts
│   │   │   │   │   ├── comment-list.component.html
│   │   │   │   │   └── comment-list.component.scss
│   │   │   │   └── comment-form/
│   │   │   │       ├── comment-form.component.ts
│   │   │   │       ├── comment-form.component.html
│   │   │   │       └── comment-form.component.scss
│   │   │   ├── services/
│   │   │   │   ├── post.service.ts
│   │   │   │   ├── comment.service.ts
│   │   │   │   └── like.service.ts
│   │   │   ├── models/
│   │   │   │   ├── post.model.ts
│   │   │   │   ├── comment.model.ts
│   │   │   │   └── like.model.ts
│   │   │   └── posts.module.ts
│   │   │
│   │   ├── profile/
│   │   │   ├── components/
│   │   │   │   ├── user-profile/
│   │   │   │   │   ├── user-profile.component.ts
│   │   │   │   │   ├── user-profile.component.html
│   │   │   │   │   └── user-profile.component.scss
│   │   │   │   ├── user-posts/
│   │   │   │   └── edit-profile/
│   │   │   ├── services/
│   │   │   │   ├── user.service.ts
│   │   │   │   └── subscription.service.ts
│   │   │   ├── models/
│   │   │   │   └── profile.model.ts
│   │   │   └── profile.module.ts
│   │   │
│   │   ├── feed/
│   │   │   ├── components/
│   │   │   │   └── feed/
│   │   │   │       ├── feed.component.ts
│   │   │   │       ├── feed.component.html
│   │   │   │       └── feed.component.scss
│   │   │   ├── services/
│   │   │   │   └── feed.service.ts
│   │   │   └── feed.module.ts
│   │   │
│   │   ├── admin/
│   │   │   ├── components/
│   │   │   │   ├── dashboard/
│   │   │   │   ├── user-management/
│   │   │   │   ├── post-management/
│   │   │   │   └── report-management/
│   │   │   ├── services/
│   │   │   │   ├── admin-user.service.ts
│   │   │   │   ├── admin-post.service.ts
│   │   │   │   └── admin-report.service.ts
│   │   │   └── admin.module.ts
│   │   │
│   │   └── notifications/
│   │       ├── components/
│   │       │   └── notification-list/
│   │       ├── services/
│   │       │   └── notification.service.ts
│   │       └── notifications.module.ts
│   │
│   ├── shared/                        # Shared components/utilities
│   │   ├── components/
│   │   │   ├── header/
│   │   │   │   ├── header.component.ts
│   │   │   │   ├── header.component.html
│   │   │   │   └── header.component.scss
│   │   │   ├── sidebar/
│   │   │   ├── loading-spinner/
│   │   │   ├── confirmation-dialog/
│   │   │   └── media-upload/
│   │   │       ├── media-upload.component.ts
│   │   │       ├── media-upload.component.html
│   │   │       └── media-upload.component.scss
│   │   ├── pipes/
│   │   │   ├── time-ago.pipe.ts
│   │   │   └── truncate.pipe.ts
│   │   ├── directives/
│   │   │   └── lazy-load-image.directive.ts
│   │   └── shared.module.ts
│   │
│   ├── layouts/                       # Layout wrappers
│   │   ├── main-layout/
│   │   │   ├── main-layout.component.ts
│   │   │   ├── main-layout.component.html
│   │   │   └── main-layout.component.scss
│   │   ├── admin-layout/
│   │   └── auth-layout/
│   │
│   ├── app.component.ts
│   ├── app.component.html
│   ├── app.routes.ts                 # Angular 17+ standalone routing
│   └── app.config.ts
│
├── assets/
│   ├── images/
│   ├── icons/
│   └── styles/
│       ├── _variables.scss
│       ├── _mixins.scss
│       └── _theme.scss
│
├── environments/
│   ├── environment.ts
│   └── environment.prod.ts
│
└── styles.scss```