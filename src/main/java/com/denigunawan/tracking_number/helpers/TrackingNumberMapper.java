package com.denigunawan.tracking_number.helpers;

import com.denigunawan.tracking_number.dtos.TrackingNumberRequest;
import com.denigunawan.tracking_number.models.TrackingNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackingNumberMapper {

    TrackingNumber toEntity(TrackingNumberRequest dto);
}