package com.hostels.beans;

import io.micronaut.configuration.hibernate.jpa.proxy.GenerateProxy;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Serdeable
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@GenerateProxy
public class Hostels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hostelId;
    private String hostelName;
    private String hostelAddress;
}