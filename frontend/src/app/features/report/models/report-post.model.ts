export interface ReportPayload {
  userId: string;
  postId?: string;
  commentId?: string;
  reported: string;
  category: string;
  reason: string;
}
