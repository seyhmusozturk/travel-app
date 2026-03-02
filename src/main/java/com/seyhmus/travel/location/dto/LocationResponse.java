package com.seyhmus.travel.location.dto;

public class LocationResponse {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String locationCode;

    public LocationResponse(Long id, String name, String country, String city, String locationCode) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.locationCode = locationCode;
    }

    public LocationResponse() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getLocationCode() { return locationCode; }
}