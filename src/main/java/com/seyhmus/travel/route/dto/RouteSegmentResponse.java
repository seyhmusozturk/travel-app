package com.seyhmus.travel.route.dto;

import com.seyhmus.travel.transportation.TransportationType;

public class RouteSegmentResponse {

    private Long transportationId;
    private TransportationType type;
    private LocationShortResponse origin;
    private LocationShortResponse destination;

    public RouteSegmentResponse() {}

    public RouteSegmentResponse(Long transportationId,
                                TransportationType type,
                                LocationShortResponse origin,
                                LocationShortResponse destination) {
        this.transportationId = transportationId;
        this.type = type;
        this.origin = origin;
        this.destination = destination;
    }

    public Long getTransportationId() { return transportationId; }
    public TransportationType getType() { return type; }
    public LocationShortResponse getOrigin() { return origin; }
    public LocationShortResponse getDestination() { return destination; }

    public void setTransportationId(Long transportationId) { this.transportationId = transportationId; }
    public void setType(TransportationType type) { this.type = type; }
    public void setOrigin(LocationShortResponse origin) { this.origin = origin; }
    public void setDestination(LocationShortResponse destination) { this.destination = destination; }
}