package com.seyhmus.travel.transportation;

import com.seyhmus.travel.common.exception.ResourceNotFoundException;
import com.seyhmus.travel.location.LocationEntity;
import com.seyhmus.travel.location.LocationRepository;
import com.seyhmus.travel.transportation.dto.TransportationCreateRequest;
import com.seyhmus.travel.transportation.dto.TransportationResponse;
import com.seyhmus.travel.transportation.dto.TransportationUpdateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TransportationService {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationService(TransportationRepository transportationRepository,
                                 LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }


    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public TransportationResponse create(TransportationCreateRequest req) {
        LocationEntity origin = getLocation(req.getOriginLocationId(), "originLocationId");
        LocationEntity destination = getLocation(req.getDestinationLocationId(), "destinationLocationId");

        validateOriginDestination(origin.getId(), destination.getId());

        List<Integer> days = normalizeDays(req.getOperatingDays());

        TransportationEntity entity = new TransportationEntity(origin, destination, req.getType(), days);
        TransportationEntity saved = transportationRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TransportationResponse> list() {
        return transportationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransportationResponse get(Long id) {
        TransportationEntity entity = transportationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportation not found: id=" + id));
        return toResponse(entity);
    }


    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public TransportationResponse update(Long id, TransportationUpdateRequest req) {
        TransportationEntity entity = transportationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportation not found: id=" + id));

        LocationEntity origin = getLocation(req.getOriginLocationId(), "originLocationId");
        LocationEntity destination = getLocation(req.getDestinationLocationId(), "destinationLocationId");

        validateOriginDestination(origin.getId(), destination.getId());

        entity.setOriginLocation(origin);
        entity.setDestinationLocation(destination);
        entity.setType(req.getType());
        entity.setOperatingDays(normalizeDays(req.getOperatingDays()));

        TransportationEntity saved = transportationRepository.save(entity);
        return toResponse(saved);
    }


    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public void delete(Long id) {
        if (!transportationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transportation not found: id=" + id);
        }
        transportationRepository.deleteById(id);
    }

    private LocationEntity getLocation(Long id, String fieldName) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location not found for " + fieldName + ": id=" + id
                ));
    }

    private void validateOriginDestination(Long originId, Long destinationId) {
        if (originId.equals(destinationId)) {
            throw new IllegalArgumentException("originLocationId and destinationLocationId must be different");
        }
    }


    private List<Integer> normalizeDays(List<Integer> days) {
        if (days == null) {
            throw new IllegalArgumentException("operatingDays must not be null");
        }
        return days.stream()
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private TransportationResponse toResponse(TransportationEntity e) {
        return new TransportationResponse(
                e.getId(),
                e.getOriginLocation().getId(), e.getOriginLocation().getLocationCode(),
                e.getDestinationLocation().getId(), e.getDestinationLocation().getLocationCode(),
                e.getType(),
                e.getOperatingDays()
        );
    }
}