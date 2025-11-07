package com.divefinder.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CommentDto {

    @NotEmpty(message = "Comment text cannot be empty")
    @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
    private String commentText;

    @NotNull(message = "Date created cannot be null")
    private LocalDate dateCreated;

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Dive site ID cannot be null")
    private Integer diveSiteId;

    public CommentDto() {
    }

    public CommentDto(String commentText, LocalDate dateCreated, Integer userId, Integer diveSiteId) {
        this.commentText = commentText;
        this.dateCreated = dateCreated;
        this.userId = userId;
        this.diveSiteId = diveSiteId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDiveSiteId() {
        return diveSiteId;
    }

    public void setDiveSiteId(Integer diveSiteId) {
        this.diveSiteId = diveSiteId;
    }
}

