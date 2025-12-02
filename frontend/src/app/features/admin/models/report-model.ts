import { Author } from '../../posts/models/author-model';

export interface Report {
  id: string;
  reporterUser?: Author;
  reportedUser?: Author;
  reportedType: string;
  reportedPostId?: string;
  reportedCommentId?: string;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  category: string;
  reason: string;
  createdAt: string;
}
