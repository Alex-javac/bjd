package com.bjd.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "departure_time")
    private LocalDate departureTime;
    @Column(name = "arrival_time")
    private LocalDate arrivalTime;
    @Column(name = "price")
    private Double price;
    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_station_id", nullable = false)
    private StationEntity departureStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_station_id", nullable = false)
    private StationEntity arrivalStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private TrainEntity train;

    public RouteEntity(Long id) {
        this.id = id;
    }
}
