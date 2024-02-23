package com.azshiptest.azshipapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "shipments_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentInfo {

    @Id
    private String trackingID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_address_id")
    private Address senderAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_address_id")
    private Address recipientAddress;

    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum shipmentStatus;

    private LocalDate postingDate;
    private LocalDate estimatedArrivalDate;
    private BigDecimal value;
    private Integer weight;
    private Float cubingMeasurement;
}
