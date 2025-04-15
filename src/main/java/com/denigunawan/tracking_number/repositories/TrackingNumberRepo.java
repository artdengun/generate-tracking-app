package com.denigunawan.tracking_number.repositories;

import com.denigunawan.tracking_number.models.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingNumberRepo extends JpaRepository<TrackingNumber, Long> {
}
