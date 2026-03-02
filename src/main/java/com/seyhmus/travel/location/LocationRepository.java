package com.seyhmus.travel.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByLocationCode(String locationCode);
    boolean existsByLocationCode(String locationCode);
}