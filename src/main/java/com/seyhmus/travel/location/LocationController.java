package com.seyhmus.travel.location;

import com.seyhmus.travel.location.dto.LocationCreateRequest;
import com.seyhmus.travel.location.dto.LocationResponse;
import com.seyhmus.travel.location.dto.LocationUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponse create(@Valid @RequestBody LocationCreateRequest req) {
        return locationService.create(req);
    }

    @GetMapping
    public List<LocationResponse> list() {
        return locationService.list();
    }

    @GetMapping("/{id}")
    public LocationResponse get(@PathVariable("id") Long id) {
        return locationService.get(id);
    }

    @PutMapping("/{id}")
    public LocationResponse update(@PathVariable("id") Long id,
                                   @Valid @RequestBody LocationUpdateRequest request) {
        return locationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}