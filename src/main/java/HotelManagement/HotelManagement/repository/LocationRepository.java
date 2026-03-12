package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}

