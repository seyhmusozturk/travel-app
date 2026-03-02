package com.seyhmus.travel.route.dto;

import java.util.List;

public class RouteResponse {
    private List<RouteSegmentResponse> segments;

    public RouteResponse() {}

    public RouteResponse(List<RouteSegmentResponse> segments) {
        this.segments = segments;
    }

    public List<RouteSegmentResponse> getSegments() {
        return segments;
    }

    public void setSegments(List<RouteSegmentResponse> segments) {
        this.segments = segments;
    }
}