package com.seyhmus.travel.route.dto;

public class LocationShortResponse {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String locationCode;

    public LocationShortResponse() {}

    public LocationShortResponse(Long id, String name, String country, String city, String locationCode) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.locationCode = locationCode;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getLocationCode() { return locationCode; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }
}