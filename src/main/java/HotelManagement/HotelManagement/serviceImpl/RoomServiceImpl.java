package HotelManagement.HotelManagement.serviceImpl;

import HotelManagement.HotelManagement.dto.RoomRequestDto;
import HotelManagement.HotelManagement.dto.RoomResponseDto;
import HotelManagement.HotelManagement.entity.Location;
import HotelManagement.HotelManagement.entity.Room;
import HotelManagement.HotelManagement.exception.BadRequestException;
import HotelManagement.HotelManagement.exception.ResourceNotFoundException;
import HotelManagement.HotelManagement.repository.LocationRepository;
import HotelManagement.HotelManagement.repository.RoomRepository;
import HotelManagement.HotelManagement.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           LocationRepository locationRepository) {
        this.roomRepository = roomRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public RoomResponseDto createRoom(RoomRequestDto dto) {
        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())) {
            throw new BadRequestException("Room number already exists");
        }
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + dto.getLocationId()));

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPrice(dto.getPrice());
        room.setStatus(dto.getStatus());
        room.setLocation(location);

        Room saved = roomRepository.save(room);
        return toDto(saved);
    }

    @Override
    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        return toDto(room);
    }

    @Override
    public RoomResponseDto updateRoom(Long id, RoomRequestDto dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + dto.getLocationId()));

        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPrice(dto.getPrice());
        room.setStatus(dto.getStatus());
        room.setLocation(location);
        return toDto(room);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        roomRepository.delete(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream().map(this::toDto).toList();
    }

    private RoomResponseDto toDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setPrice(room.getPrice());
        dto.setStatus(room.getStatus());
        if (room.getLocation() != null) {
            dto.setLocationId(room.getLocation().getId());
            dto.setDistrict(room.getLocation().getDistrict());
            dto.setProvinceCode(room.getLocation().getProvince().getProvinceCode());
        }
        return dto;
    }
}

