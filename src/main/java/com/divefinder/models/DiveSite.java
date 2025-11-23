package com.divefinder.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dive_sites")
public class DiveSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String description;

    private String siteName;
    private double latitude;
    private double longitude;

    private boolean isApproved;
    @OneToMany(mappedBy = "diveSite")
    private Set<Comment> comments = new HashSet<>();



    public DiveSite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setDiveSite(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setDiveSite(null);
    }
}
