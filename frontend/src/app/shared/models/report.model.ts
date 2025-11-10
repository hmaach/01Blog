export interface ReportPayload {
  reportedUserId: string;
  reportedPostId?: string;
  reportedCommentId?: string;
  reportType: string; // post | user | comment
  category: string;
  reason: string;
}
