package com.seyhmus.travel.transportation.dto;

import com.seyhmus.travel.transportation.TransportationType;
import jakarta.validation.constraints.*;

import java.util.List;

public class TransportationCreateRequest {

    @NotNull(message = "originLocationId must not be null")
    private Long originLocationId;

    @NotNull(message = "destinationLocationId must not be null")
    private Long destinationLocationId;

    @NotNull(message = "type must not be null")
    private TransportationType type;

    @NotNull(message = "operatingDays must not be null")
    @Size(min = 1, message = "operatingDays must have at least 1 day")
    private List<@Min(value = 1, message = "operatingDays values must be between 1 and 7")
    @Max(value = 7, message = "operatingDays values must be between 1 and 7") Integer> operatingDays;

    public TransportationCreateRequest() {}

    public Long getOriginLocationId() { return originLocationId; }
    public Long getDestinationLocationId() { return destinationLocationId; }
    public TransportationType getType() { return type; }
    public List<Integer> getOperatingDays() { return operatingDays; }

    public void setOriginLocationId(Long originLocationId) { this.originLocationId = originLocationId; }
    public void setDestinationLocationId(Long destinationLocationId) { this.destinationLocationId = destinationLocationId; }
    public void setType(TransportationType type) { this.type = type; }
    public void setOperatingDays(List<Integer> operatingDays) { this.operatingDays = operatingDays; }
}