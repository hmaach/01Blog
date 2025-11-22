import { Author } from '../../posts/models/author-model';

export interface Report {
  id: string;
  reporterUser?: Author;
  reportedUser?: Author;
  reportedType: string;
  reportedPostId?: string;
  reportedCommentId?: string;
  status: string;
  category: string;
  reason: string;
  createdAt: string;
}
