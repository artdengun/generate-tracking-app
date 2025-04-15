package com.denigunawan.tracking_number.controllers;

import com.denigunawan.tracking_number.dtos.TrackingNumberRequest;
import com.denigunawan.tracking_number.services.GenerateTrackingNumberServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class GenerateTrackingNumberController {

  @Autowired
  GenerateTrackingNumberServices generateTrackingNumberServices;

  @GetMapping("/next-tracking-number")
  @Operation(summary = "Generate next tracking number", description = "Generates a unique tracking number")
  public ResponseEntity<Map<String, String>> getTrackingNumber(@RequestParam("origin_country_id") String originCountryId,
                                                               @RequestParam("destination_country_id") String destinationCountryId,
                                                               @RequestParam("weight") BigDecimal weight,
                                                               @RequestParam("created_at") String createdAt,
                                                               @RequestParam("customer_id") String customerId,
                                                               @RequestParam("customer_name") String customerName,
                                                               @RequestParam("customer_slug") String customerSlug) {
      Map<String, String> response = new HashMap<>();
    try {
       TrackingNumberRequest trackingRequest =  TrackingNumberRequest.builder()
                .originCountryId(originCountryId)
                .destinationCountryId(destinationCountryId)
                .weight(weight)
                .createdAt(createdAt)
                .customerId(customerId)
                .customerName(customerName)
                .customerSlug(customerSlug)
                .build();
       log.info("Tracking Result : {}", trackingRequest);
        String trackingNumber = generateTrackingNumberServices.generateTrackingNumber(trackingRequest);
        response.put("tracking_number", trackingNumber);
        response.put("created_at", Instant.now().toString());
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        log.error("Error generating tracking number", e);
        response.put("error", "An error occurred while generating the tracking number");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}
