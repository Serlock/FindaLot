package com.example.googlemapsdemo;

public class Location {
    private int id;
    private int userId;
    private double longitude;
    private double latitude;
    private String AddressLine;
    private String CountryName;
    private String AdminArea;
    private String SubAdminArea;
    private boolean isExpanded;

    public Location(int id, int userId, double longitude, double latitude, String addressLine, String countryName, String adminArea, String subAdminArea) {
        this.id = id;
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        AddressLine = addressLine;
        CountryName = countryName;
        AdminArea = adminArea;
        SubAdminArea = subAdminArea;
        isExpanded = false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String addressLine) {
        AddressLine = addressLine;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getAdminArea() {
        return AdminArea;
    }

    public void setAdminArea(String adminArea) {
        AdminArea = adminArea;
    }

    public String getSubAdminArea() {
        return SubAdminArea;
    }

    public void setSubAdminArea(String subAdminArea) {
        SubAdminArea = subAdminArea;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", userId=" + userId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", AddressLine='" + AddressLine + '\'' +
                ", CountryName='" + CountryName + '\'' +
                ", AdminArea='" + AdminArea + '\'' +
                ", SubAdminArea='" + SubAdminArea + '\'' +
                '}';
    }
}
