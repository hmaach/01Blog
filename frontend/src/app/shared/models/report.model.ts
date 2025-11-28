export interface ReportPayload {
  reportedUserId: string;
  reportedPostId?: string;
  reportedCommentId?: string;
  reportedType: string; // post | user | comment
  category: string;
  reason: string;
}
