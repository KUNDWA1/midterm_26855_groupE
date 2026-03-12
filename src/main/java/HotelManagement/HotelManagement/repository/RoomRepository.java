package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    boolean existsByRoomNumber(String roomNumber);
}

