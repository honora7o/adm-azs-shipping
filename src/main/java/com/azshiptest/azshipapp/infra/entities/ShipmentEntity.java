package com.azshiptest.azshipapp.infra.entities;

import com.azshiptest.azshipapp.models.ShipmentStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "shipments_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID trackingNo;

    private String taxPayerRegistrationNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_address_id")
    private AddressEntity senderAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_address_id")
    private AddressEntity recipientAddress;

    @Enumerated(EnumType.STRING)
    private ShipmentStatusEnum shipmentStatus;

    private LocalDate postingDate;
    private LocalDate estimatedArrivalDate;
    private BigDecimal value;
    private Integer weight;
    private Float cubingMeasurement;

    @PrePersist
    private void ensureTrackingNo() {
        if (trackingNo == null) {
            trackingNo = UUID.randomUUID();
        }
    }
}
