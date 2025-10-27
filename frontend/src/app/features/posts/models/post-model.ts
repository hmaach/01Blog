import { Media } from './media-model';

export interface Post {
  id: string;
  title: string;
  body: string;
  likesCount: number;
  commentsCount: number;
  impressionsCount: number;
  author: {
    id: string;
    username: string;
    name: string;
    avatar?: string;
  };
  media?: Media[];
  createdAt: string;
  isHidden?: boolean;
}
