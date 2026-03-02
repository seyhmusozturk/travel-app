package com.seyhmus.travel.transportation;

import com.seyhmus.travel.transportation.dto.TransportationCreateRequest;
import com.seyhmus.travel.transportation.dto.TransportationResponse;
import com.seyhmus.travel.transportation.dto.TransportationUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportations")
public class TransportationController {

    private final TransportationService transportationService;

    public TransportationController(TransportationService transportationService) {
        this.transportationService = transportationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransportationResponse create(@Valid @RequestBody TransportationCreateRequest req) {
        return transportationService.create(req);
    }

    @GetMapping
    public List<TransportationResponse> list() {
        return transportationService.list();
    }

    @GetMapping("/{id}")
    public TransportationResponse get(@PathVariable("id") Long id) {
        return transportationService.get(id);
    }

    @PutMapping("/{id}")
    public TransportationResponse update(@PathVariable("id") Long id,
                                         @Valid @RequestBody TransportationUpdateRequest req) {
        return transportationService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        transportationService.delete(id);
    }
}