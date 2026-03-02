package com.seyhmus.travel.route;

import com.seyhmus.travel.route.dto.RouteResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<RouteResponse> listRoutes(
            @RequestParam("originId") @NotNull Long originId,
            @RequestParam("destinationId") @NotNull Long destinationId,
            @RequestParam("date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return routeService.listRoutes(originId, destinationId, date);
    }
}