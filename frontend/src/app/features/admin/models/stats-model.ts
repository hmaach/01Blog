import { UserResponse } from '../../profile/models/user-response.model';
import { Report } from './report-model';

export interface AdminStats {
  totalUsers: number;
  totalPosts: number;
  totalReports: number;
  totalUserReports: number;
  totalPostReports: number;
  totalCommentReports: number;
  threeActiveUsers: UserResponse[];
  lastThreeReports: Report[];
}
