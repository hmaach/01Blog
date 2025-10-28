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
  media?: Media[];
  createdAt: string;
  isHidden?: boolean;
  isOwner: boolean;
  isLiked: boolean;
}
