package HotelManagement.HotelManagement.service;

import HotelManagement.HotelManagement.dto.LocationRequestDto;
import HotelManagement.HotelManagement.dto.LocationResponseDto;

import java.util.List;

public interface LocationService {
    LocationResponseDto createLocation(LocationRequestDto dto);
    List<LocationResponseDto> getAllLocations();
}

