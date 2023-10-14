package ru.dedov.moretechbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "place_load")
@RequiredArgsConstructor
@AllArgsConstructor
public class PlaceLoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    private String hourOfDay;

    @Column(name = "load")
    private Double load;
}