package com.seyhmus.travel.transportation;

import com.seyhmus.travel.location.LocationEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transportations")
public class TransportationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "origin_location_id", nullable = false)
    private LocationEntity originLocation;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destination_location_id", nullable = false)
    private LocationEntity destinationLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "transportation_type", nullable = false, length = 20)
    private TransportationType type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "transportation_operating_days",
            joinColumns = @JoinColumn(name = "transportation_id")
    )
    @Column(name = "day_of_week", nullable = false)
    private List<Integer> operatingDays;

    public TransportationEntity() {}

    public TransportationEntity(LocationEntity originLocation,
                                LocationEntity destinationLocation,
                                TransportationType type,
                                List<Integer> operatingDays) {
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.type = type;
        this.operatingDays = operatingDays;
    }

    public Long getId() { return id; }
    public LocationEntity getOriginLocation() { return originLocation; }
    public LocationEntity getDestinationLocation() { return destinationLocation; }
    public TransportationType getType() { return type; }
    public List<Integer> getOperatingDays() { return operatingDays; }

    public void setId(Long id) { this.id = id; }
    public void setOriginLocation(LocationEntity originLocation) { this.originLocation = originLocation; }
    public void setDestinationLocation(LocationEntity destinationLocation) { this.destinationLocation = destinationLocation; }
    public void setType(TransportationType type) { this.type = type; }
    public void setOperatingDays(List<Integer> operatingDays) { this.operatingDays = operatingDays; }
}