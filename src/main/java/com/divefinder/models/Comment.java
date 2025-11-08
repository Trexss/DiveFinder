package com.divefinder.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dive_site_id")
    private DiveSite diveSite;

    public Comment() {
    }

    public Comment(String commentText, LocalDate dateCreated, User user, DiveSite diveSite) {
        this.commentText = commentText;
        this.dateCreated = dateCreated;
        this.user = user;
        this.diveSite = diveSite;
    }
    @PrePersist
    public void prePersist() {
        if (dateCreated == null) dateCreated = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DiveSite getDiveSite() {
        return diveSite;
    }

    public void setDiveSite(DiveSite diveSite) {
        this.diveSite = diveSite;
    }
}

