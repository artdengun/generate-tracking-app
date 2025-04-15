package com.denigunawan.tracking_number.dtos;

import com.denigunawan.tracking_number.models.TrackingNumber;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TrackingNumberRequest {
    private String originCountryId;
    private String destinationCountryId;
    private BigDecimal weight;
    private String createdAt;
    private String customerId;
    private String customerName;
    private String customerSlug;
}



