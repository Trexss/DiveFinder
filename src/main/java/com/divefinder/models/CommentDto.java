package com.divefinder.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CommentDto {

    @NotEmpty(message = "Comment text cannot be empty")
    @Size(max = 1000, message = "Comment text cannot exceed 1000 characters")
    private String commentText;


    private LocalDate dateCreated;


    private Integer userId;


    private Integer diveSiteId;

    public CommentDto() {
    }

    public CommentDto(String commentText, Integer userId) {
        this.commentText = commentText;
        this.userId = userId;

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

