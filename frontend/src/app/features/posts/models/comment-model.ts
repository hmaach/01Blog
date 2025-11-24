import { Author } from './author-model';

export interface Comment {
  id: string;
  postId: string;
  isOwner?: boolean;
  author: Author;
  content: string;
  createdAt: string;
}
