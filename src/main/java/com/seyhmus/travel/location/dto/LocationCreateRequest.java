package com.seyhmus.travel.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LocationCreateRequest {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "country must not be blank")
    private String country;

    @NotBlank(message = "city must not be blank")
    private String city;

    @NotBlank(message = "locationCode must not be blank")
    @Size(min = 2, max = 10, message = "locationCode length must be between 2 and 10")
    private String locationCode;

    public LocationCreateRequest() {}

    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getLocationCode() { return locationCode; }

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }
}