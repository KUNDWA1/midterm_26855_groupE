package HotelManagement.HotelManagement.controller;

import HotelManagement.HotelManagement.dto.LocationRequestDto;
import HotelManagement.HotelManagement.dto.LocationResponseDto;
import HotelManagement.HotelManagement.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Location saving endpoint (assessment requirement).
     */
    @PostMapping
    public ResponseEntity<LocationResponseDto> create(@Valid @RequestBody LocationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createLocation(dto));
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDto>> getAll() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}

