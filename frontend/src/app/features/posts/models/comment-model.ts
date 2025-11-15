import { Author } from './author-model';

export interface Comment {
  id: string;
  postId: string;
  author: Author;
  content: string;
  createdAt: string;
}
