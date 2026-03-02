package com.seyhmus.travel.transportation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransportationRepository extends JpaRepository<TransportationEntity, Long> {

    List<TransportationEntity> findAllByOriginLocation_Id(Long originLocationId);

    List<TransportationEntity> findAllByDestinationLocation_Id(Long destinationLocationId);

    List<TransportationEntity> findAllByOriginLocation_IdAndDestinationLocation_Id(Long originLocationId, Long destinationLocationId);

    List<TransportationEntity> findAllByType(TransportationType type);

    List<TransportationEntity> findAllByTypeAndOriginLocation_Id(TransportationType type, Long originLocationId);

    List<TransportationEntity> findAllByTypeAndDestinationLocation_Id(TransportationType type, Long destinationLocationId);

    @Override
    @EntityGraph(attributePaths = {"operatingDays", "originLocation", "destinationLocation"})
    List<TransportationEntity> findAll();

    @Override
    @EntityGraph(attributePaths = {"operatingDays", "originLocation", "destinationLocation"})
    Optional<TransportationEntity> findById(Long id);
}