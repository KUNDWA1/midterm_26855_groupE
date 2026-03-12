package HotelManagement.HotelManagement.serviceImpl;

import HotelManagement.HotelManagement.dto.LocationRequestDto;
import HotelManagement.HotelManagement.dto.LocationResponseDto;
import HotelManagement.HotelManagement.entity.Location;
import HotelManagement.HotelManagement.entity.Province;
import HotelManagement.HotelManagement.exception.ResourceNotFoundException;
import HotelManagement.HotelManagement.repository.LocationRepository;
import HotelManagement.HotelManagement.repository.ProvinceRepository;
import HotelManagement.HotelManagement.service.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ProvinceRepository provinceRepository;

    public LocationServiceImpl(LocationRepository locationRepository,
                               ProvinceRepository provinceRepository) {
        this.locationRepository = locationRepository;
        this.provinceRepository = provinceRepository;
    }

    /**
     * Location saving logic:
     * - We first load the Province using the provided provinceCode
     * - Then we create a Location and assign that Province
     * - JPA stores the foreign key province_id in the locations table
     */
    @Override
    public LocationResponseDto createLocation(LocationRequestDto dto) {
        Province province = provinceRepository.findByProvinceCode(dto.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with code: " + dto.getProvinceCode()));

        Location location = new Location();
        location.setProvince(province);
        location.setDistrict(dto.getDistrict());
        location.setSector(dto.getSector());
        location.setCell(dto.getCell());
        location.setVillage(dto.getVillage());

        Location saved = locationRepository.save(location);
        return toDto(saved);
    }

    @Override
    public List<LocationResponseDto> getAllLocations() {
        return locationRepository.findAll().stream().map(this::toDto).toList();
    }

    private LocationResponseDto toDto(Location location) {
        LocationResponseDto dto = new LocationResponseDto();
        dto.setId(location.getId());
        dto.setDistrict(location.getDistrict());
        dto.setSector(location.getSector());
        dto.setCell(location.getCell());
        dto.setVillage(location.getVillage());
        dto.setProvinceCode(location.getProvince().getProvinceCode());
        dto.setProvinceName(location.getProvince().getProvinceName());
        return dto;
    }
}

