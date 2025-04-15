package com.denigunawan.tracking_number.services;

import com.denigunawan.tracking_number.helpers.HelperUtils;
import com.denigunawan.tracking_number.dtos.TrackingNumberRequest;
import com.denigunawan.tracking_number.exceptions.GenerateTrackingNumberException;
import com.denigunawan.tracking_number.helpers.TrackingNumberMapper;
import com.denigunawan.tracking_number.models.TrackingNumber;
import com.denigunawan.tracking_number.repositories.TrackingNumberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class GenerateTrackingNumberServices {

 @Autowired
 private RedisTemplate<String, String> redisTemplate;
 @Autowired
 private TrackingNumberRepo insertTrackingNumberReport;
 @Autowired
 private TrackingNumberMapper trackingNumberMapper;

/**
 * Generates a unique tracking number based on shipment and customer data.
 * <p>
 * This method checks Redis cache first. If not found, it creates a new tracking number
 * using hashed request data and a timestamp to ensure uniqueness,
 * then stores it in Redis for faster future access.
 *
 * @param trackingNumberRequest the input data for generating the tracking number
 * @return a unique tracking number (max 16 characters)
 * @throws GenerateTrackingNumberException if generation or Redis access fails
 */
public String generateTrackingNumber(TrackingNumberRequest trackingNumberRequest) throws GenerateTrackingNumberException {
    try {
        String origin = trackingNumberRequest.getOriginCountryId();
        String destination = trackingNumberRequest.getDestinationCountryId();
        BigDecimal weight = trackingNumberRequest.getWeight();
        String createdAt = trackingNumberRequest.getCreatedAt();
        String customerId = trackingNumberRequest.getCustomerId();
        String customerName = trackingNumberRequest.getCustomerName();
        String customerSlug = trackingNumberRequest.getCustomerSlug();

        String cacheKey = String.join(":", origin, destination, weight.toPlainString(), createdAt, customerId, customerName, customerSlug);
        String cached = redisTemplate.opsForValue().get(cacheKey);

        if (cached != null) {
            log.info("Tracking number found in cache for key: {}", cacheKey);
            return cached;
        }

        String prefix = (origin + destination).toUpperCase();
        String rawData = weight.toPlainString() +
                createdAt +
                customerId +
                customerName +
                customerSlug +
                Instant.now().toString();

        String hashPart = HelperUtils.hashToBase36(rawData);
        String trackingNumber = (prefix + hashPart).toUpperCase();
        trackingNumber = trackingNumber.length() > 16 ? trackingNumber.substring(0, 16) : trackingNumber;
        log.info("Request DTO: {}", trackingNumberRequest);
        TrackingNumber history = trackingNumberMapper.toEntity(trackingNumberRequest);

        redisTemplate.opsForValue().set(cacheKey, trackingNumber, Duration.ofHours(1));
        log.info("History: {}", history);
        insertTrackingNumberReport.save(history);
        log.info("Generated new tracking number: {}", trackingNumber);
        return trackingNumber;

    } catch (Exception e) {
        log.error("Error generating tracking number", e);
        throw new GenerateTrackingNumberException("Failed to generate tracking number");
    }
  }
}
