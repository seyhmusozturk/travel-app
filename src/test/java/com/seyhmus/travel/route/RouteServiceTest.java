package com.seyhmus.travel.route;

import com.seyhmus.travel.location.LocationEntity;
import com.seyhmus.travel.location.LocationRepository;
import com.seyhmus.travel.route.dto.RouteResponse;
import com.seyhmus.travel.transportation.TransportationEntity;
import com.seyhmus.travel.transportation.TransportationRepository;
import com.seyhmus.travel.transportation.TransportationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class RouteServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("travel")
            .withUsername("travel")
            .withPassword("travel");

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.cache.type", () -> "none");
    }

    @Autowired RouteService routeService;
    @Autowired LocationRepository locationRepository;
    @Autowired TransportationRepository transportationRepository;

    private Long istId;
    private Long ankId;
    private Long tkmId;

    @BeforeEach
    void setup() {
        transportationRepository.deleteAll();
        locationRepository.deleteAll();

        // Locations
        LocationEntity ist = locationRepository.save(new LocationEntity("Istanbul Airport", "TR", "Istanbul", "IST"));
        LocationEntity ank = locationRepository.save(new LocationEntity("Ankara", "TR", "Ankara", "ANK"));
        LocationEntity tkm = locationRepository.save(new LocationEntity("Taksim", "TR", "Istanbul", "TKM"));

        istId = ist.getId();
        ankId = ank.getId();
        tkmId = tkm.getId();

        List<Integer> wedOnly = List.of(3);


        transportationRepository.save(new TransportationEntity(ist, ank, TransportationType.UBER, wedOnly));

        transportationRepository.save(new TransportationEntity(ank, tkm, TransportationType.FLIGHT, wedOnly));
    }

    @Test
    void listRoutes_shouldReturnBeforePlusFlight() {
        LocalDate date = LocalDate.of(2026, 3, 4);

        List<RouteResponse> routes = routeService.listRoutes(istId, tkmId, date);


        assertThat(routes).isNotNull();
        assertThat(routes).hasSize(1);
        assertThat(routes.get(0).getSegments()).hasSize(2);
        assertThat(routes.get(0).getSegments().get(0).getType()).isEqualTo(TransportationType.UBER);
        assertThat(routes.get(0).getSegments().get(1).getType()).isEqualTo(TransportationType.FLIGHT);
    }

    @Test
    void listRoutes_shouldReturnEmpty_whenOperatingDaysDoesNotMatch() {
        LocalDate date = LocalDate.of(2026, 3, 5); // Thu(4)

        List<RouteResponse> routes = routeService.listRoutes(istId, tkmId, date);

        assertThat(routes).isEmpty();
    }
}