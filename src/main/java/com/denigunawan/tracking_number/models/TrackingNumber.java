package com.denigunawan.tracking_number.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Entity
@ToString
@Table(name = "tracking_number")
public class TrackingNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_country_id")
    private String originCountryId;

    @Column(name = "destination_country_id")
    private String destinationCountryId;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_slug")
    private String customerSlug;
}
