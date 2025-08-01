package org.epam.ai.dto;

public class GeoLocation {
    private double latitude;
    private double longitude;
    private String name;

    public GeoLocation(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}

