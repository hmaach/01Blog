export interface UserResponse {
  id: string;
  name: string;
  username: string;
  relation: string;
  status: 'ACTIVE' | 'BANNED';
  role: 'ADMIN' | 'USER';
  avatarUrl: string;
  postsCount: number;
  subscribersCount: number;
  impressionsCount: number;
  createdAt: string;
}

export interface CurrentUserResponse {
  id: string;
  name: string;
  username: string;
  email: string;
  emailVerified: boolean;
  avatarUrl: string;
}
