package com.seyhmus.travel.transportation.dto;

import com.seyhmus.travel.transportation.TransportationType;

import java.util.List;

public class TransportationResponse {

    private Long id;

    private Long originLocationId;
    private String originLocationCode;

    private Long destinationLocationId;
    private String destinationLocationCode;

    private TransportationType type;

    private List<Integer> operatingDays;

    public TransportationResponse() {}

    public TransportationResponse(Long id,
                                  Long originLocationId, String originLocationCode,
                                  Long destinationLocationId, String destinationLocationCode,
                                  TransportationType type,
                                  List<Integer> operatingDays) {
        this.id = id;
        this.originLocationId = originLocationId;
        this.originLocationCode = originLocationCode;
        this.destinationLocationId = destinationLocationId;
        this.destinationLocationCode = destinationLocationCode;
        this.type = type;
        this.operatingDays = operatingDays;
    }

    public Long getId() { return id; }
    public Long getOriginLocationId() { return originLocationId; }
    public String getOriginLocationCode() { return originLocationCode; }
    public Long getDestinationLocationId() { return destinationLocationId; }
    public String getDestinationLocationCode() { return destinationLocationCode; }
    public TransportationType getType() { return type; }
    public List<Integer> getOperatingDays() { return operatingDays; }
}