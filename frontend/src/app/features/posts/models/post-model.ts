import { Author } from './author-model';
import { Media } from './media-model';

export interface Post {
  id: string;
  title: string;
  body: string;
  likesCount: number;
  commentsCount: number;
  impressionsCount: number;
  author: Author;
  firstMedia?: Media;
  media?: Media[];
  createdAt: string;
  status: 'PUBLISHED' | 'HIDDEN';
  isOwner: boolean;
  isLiked: boolean;
}
