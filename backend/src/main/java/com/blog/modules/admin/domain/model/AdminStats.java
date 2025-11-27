package com.blog.modules.admin.domain.model;

public class AdminStats {

    private Integer totalUsers;
    private Integer totalPosts;
    private Integer totalReports;
    private Integer totalUserReports;
    private Integer totalPostReports;
    private Integer totalCommentReports;

    public AdminStats(
            Integer totalUsers,
            Integer totalPosts,
            Integer totalReports,
            Integer totalUserReports,
            Integer totalPostReports,
            Integer totalCommentReports) {
        this.totalUsers = totalUsers;
        this.totalPosts = totalPosts;
        this.totalReports = totalReports;
        this.totalUserReports = totalUserReports;
        this.totalPostReports = totalPostReports;
        this.totalCommentReports = totalCommentReports;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(Integer totalPosts) {
        this.totalPosts = totalPosts;
    }

    public Integer getTotalReports() {
        return totalReports;
    }

    public void setTotalReports(Integer totalReports) {
        this.totalReports = totalReports;
    }

    public Integer getTotalUserReports() {
        return totalUserReports;
    }

    public void setTotalUserReports(Integer totalUserReports) {
        this.totalUserReports = totalUserReports;
    }

    public Integer getTotalPostReports() {
        return totalPostReports;
    }

    public void setTotalPostReports(Integer totalPostReports) {
        this.totalPostReports = totalPostReports;
    }

    public Integer getTotalCommentReports() {
        return totalCommentReports;
    }

    public void setTotalCommentReports(Integer totalCommentReports) {
        this.totalCommentReports = totalCommentReports;
    }

}
