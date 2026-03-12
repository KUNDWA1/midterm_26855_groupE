package HotelManagement.HotelManagement.controller;

import HotelManagement.HotelManagement.dto.RoomRequestDto;
import HotelManagement.HotelManagement.dto.RoomResponseDto;
import HotelManagement.HotelManagement.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> create(@Valid @RequestBody RoomRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> update(@PathVariable Long id,
                                                  @Valid @RequestBody RoomRequestDto dto) {
        return ResponseEntity.ok(roomService.updateRoom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAll() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }
}

