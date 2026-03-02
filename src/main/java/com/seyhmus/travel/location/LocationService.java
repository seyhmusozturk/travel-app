package com.seyhmus.travel.location;

import com.seyhmus.travel.common.exception.ConflictException;
import com.seyhmus.travel.common.exception.ResourceNotFoundException;
import com.seyhmus.travel.location.dto.LocationCreateRequest;
import com.seyhmus.travel.location.dto.LocationResponse;
import com.seyhmus.travel.location.dto.LocationUpdateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public LocationResponse create(LocationCreateRequest req) {
        String normalizedCode = normalizeCode(req.getLocationCode());

        if (locationRepository.existsByLocationCode(normalizedCode)) {
            throw new ConflictException("Location with locationCode '" + normalizedCode + "' already exists");
        }

        LocationEntity entity = new LocationEntity(
                req.getName().trim(),
                req.getCountry().trim(),
                req.getCity().trim(),
                normalizedCode
        );

        LocationEntity saved = locationRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LocationResponse> list() {
        return locationRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LocationResponse get(Long id) {
        LocationEntity entity = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found: id=" + id));
        return toResponse(entity);
    }

    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public LocationResponse update(Long id, LocationUpdateRequest req) {
        LocationEntity entity = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found: id=" + id));

        String normalizedCode = normalizeCode(req.getLocationCode());

        if (!normalizedCode.equalsIgnoreCase(entity.getLocationCode())
                && locationRepository.existsByLocationCode(normalizedCode)) {
            throw new ConflictException("Location with locationCode '" + normalizedCode + "' already exists");
        }

        entity.setName(req.getName().trim());
        entity.setCountry(req.getCountry().trim());
        entity.setCity(req.getCity().trim());
        entity.setLocationCode(normalizedCode);

        LocationEntity saved = locationRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found: id=" + id);
        }
        locationRepository.deleteById(id);
    }

    private LocationResponse toResponse(LocationEntity e) {
        return new LocationResponse(
                e.getId(),
                e.getName(),
                e.getCountry(),
                e.getCity(),
                e.getLocationCode()
        );
    }

    private String normalizeCode(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }
}