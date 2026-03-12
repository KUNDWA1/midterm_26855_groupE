package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

