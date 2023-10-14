package ru.dedov.moretechbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "atm")
@RequiredArgsConstructor
@AllArgsConstructor
public class Atm extends Place{

    private String address;

    private Boolean allDay;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="services", joinColumns=@JoinColumn(name="atm_id"))
    private Map<String, Service> services;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PlaceLoad> placeLoads;
}
