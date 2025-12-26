import { Author } from '../../features/posts/models/author-model';

export interface Notification {
  id: string;
  postId: string;
  author: Author;
  seen: boolean;
  createdAt: string;
}
