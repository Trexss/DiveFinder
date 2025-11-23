package com.divefinder.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DiveSiteDto {
    //used only in response from map api calls to generate URL
        private int id;

        @NotNull
        @Size(min = 3, max = 30, message = "Name must be between 3 & 30 symbols")
        private String siteName;
        @NotNull
        @Size(min = 20,  message = "Description must be at least 20 symbols")
        private String description;
        @NotNull
        private double latitude;
        @NotNull
        private double longitude;
        private boolean approved = false;


        public DiveSiteDto() {
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiveSiteDto(String siteName, String description, double latitude, double longtitude) {

            this.siteName = siteName;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longtitude;

        }

    public DiveSiteDto(String siteName, String description, double latitude, double longitude, boolean approved) {
        this.siteName = siteName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.approved = approved;
    }

    public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
            return approved;
        }

        public void setApproved(boolean approved) {
            this.approved = approved;
        }
    }

