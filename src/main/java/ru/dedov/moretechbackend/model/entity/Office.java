package ru.dedov.moretechbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "office")
@RequiredArgsConstructor
@AllArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String salePointName;

    private String address;

    private String status;

    private String rko;

    private String officeType;

    private String salePointFormat;

    private String suoAvailability;

    private String hasRamp;

    private Double latitude;

    private Double longitude;

    private String metroStation;

    private Integer distance;

    private Boolean kep;

    private Boolean myBranch;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OpenHour> openHours;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OpenHour> openHoursIndividual;
}
