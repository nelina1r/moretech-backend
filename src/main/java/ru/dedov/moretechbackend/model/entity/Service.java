package ru.dedov.moretechbackend.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Service {
    private String serviceCapability;
    private String serviceActivity;
}
