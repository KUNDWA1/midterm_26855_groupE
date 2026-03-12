package HotelManagement.HotelManagement.service;

import HotelManagement.HotelManagement.dto.RoomRequestDto;
import HotelManagement.HotelManagement.dto.RoomResponseDto;

import java.util.List;

public interface RoomService {
    RoomResponseDto createRoom(RoomRequestDto dto);
    RoomResponseDto getRoomById(Long id);
    RoomResponseDto updateRoom(Long id, RoomRequestDto dto);
    void deleteRoom(Long id);
    List<RoomResponseDto> getAllRooms();
}

