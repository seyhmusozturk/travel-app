package com.seyhmus.travel.route;

import com.seyhmus.travel.location.LocationEntity;
import com.seyhmus.travel.location.LocationRepository;
import com.seyhmus.travel.route.dto.LocationShortResponse;
import com.seyhmus.travel.route.dto.RouteResponse;
import com.seyhmus.travel.route.dto.RouteSegmentResponse;
import com.seyhmus.travel.transportation.TransportationEntity;
import com.seyhmus.travel.transportation.TransportationRepository;
import com.seyhmus.travel.transportation.TransportationType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public RouteService(TransportationRepository transportationRepository,
                        LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "routes", key = "#originId + ':' + #destinationId + ':' + #date")
    public List<RouteResponse> listRoutes(Long originId, Long destinationId, LocalDate date) {

        LocationEntity origin = locationRepository.findById(originId)
                .orElseThrow(() -> new IllegalArgumentException("Origin location not found: id=" + originId));

        LocationEntity destination = locationRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination location not found: id=" + destinationId));

        int dow = date.getDayOfWeek().getValue(); // Mon=1 ... Sun=7

        // o gün çalışanlar
        List<TransportationEntity> todays = transportationRepository.findAll()
                .stream()
                .filter(t -> t.getOperatingDays() != null && t.getOperatingDays().contains(dow))
                .toList();

        // o gün çalışan tüm flight'lar
        List<TransportationEntity> flights = todays.stream()
                .filter(t -> t.getType() == TransportationType.FLIGHT)
                .toList();

        List<RouteResponse> routes = new ArrayList<>();

        for (TransportationEntity flight : flights) {
            Long flightOriginId = flight.getOriginLocation().getId();
            Long flightDestId = flight.getDestinationLocation().getId();

            List<TransportationEntity> befores = todays.stream()
                    .filter(t -> t.getType() != TransportationType.FLIGHT)
                    .filter(t -> t.getOriginLocation().getId().equals(originId))
                    .filter(t -> t.getDestinationLocation().getId().equals(flightOriginId))
                    .toList();

            List<TransportationEntity> afters = todays.stream()
                    .filter(t -> t.getType() != TransportationType.FLIGHT)
                    .filter(t -> t.getOriginLocation().getId().equals(flightDestId))
                    .filter(t -> t.getDestinationLocation().getId().equals(destinationId))
                    .toList();

            boolean canStart =
                    flightOriginId.equals(originId) || !befores.isEmpty();

            boolean canEnd =
                    flightDestId.equals(destinationId) || !afters.isEmpty();

            if (!canStart || !canEnd) {
                continue;
            }

            boolean directFlight = flightOriginId.equals(originId) && flightDestId.equals(destinationId);
            if (directFlight) {

                routes.add(new RouteResponse(List.of(toSegment(flight))));

                for (TransportationEntity after : afters) {
                    routes.add(new RouteResponse(List.of(toSegment(flight), toSegment(after))));
                }

                for (TransportationEntity before : befores) {
                    routes.add(new RouteResponse(List.of(toSegment(before), toSegment(flight))));
                }

                for (TransportationEntity before : befores) {
                    for (TransportationEntity after : afters) {
                        routes.add(new RouteResponse(List.of(toSegment(before), toSegment(flight), toSegment(after))));
                    }
                }
                continue;
            }


            List<List<RouteSegmentResponse>> beforeSegments = new ArrayList<>();
            if (flightOriginId.equals(originId)) {
                beforeSegments.add(List.of());
            }
            for (TransportationEntity before : befores) {
                beforeSegments.add(List.of(toSegment(before)));
            }

            List<List<RouteSegmentResponse>> afterSegments = new ArrayList<>();
            if (flightDestId.equals(destinationId)) {
                afterSegments.add(List.of());
            }
            for (TransportationEntity after : afters) {
                afterSegments.add(List.of(toSegment(after)));
            }

            for (List<RouteSegmentResponse> bs : beforeSegments) {
                for (List<RouteSegmentResponse> as : afterSegments) {
                    List<RouteSegmentResponse> segs = new ArrayList<>(3);
                    segs.addAll(bs);
                    segs.add(toSegment(flight));
                    segs.addAll(as);

                    if (segs.size() <= 3) {
                        routes.add(new RouteResponse(segs));
                    }
                }
            }
        }

        return routes;
    }

    private RouteSegmentResponse toSegment(TransportationEntity t) {
        return new RouteSegmentResponse(
                t.getId(),
                t.getType(),
                toLocationShort(t.getOriginLocation()),
                toLocationShort(t.getDestinationLocation())
        );
    }

    private LocationShortResponse toLocationShort(LocationEntity l) {
        return new LocationShortResponse(
                l.getId(),
                l.getName(),
                l.getCountry(),
                l.getCity(),
                l.getLocationCode()
        );
    }
}