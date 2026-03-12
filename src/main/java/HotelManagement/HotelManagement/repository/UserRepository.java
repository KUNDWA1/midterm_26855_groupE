package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Check if email exists
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    // Find users by province code
    List<User> findByLocationProvinceProvinceCode(String provinceCode);

    // Find users by province name
    List<User> findByLocationProvinceProvinceName(String provinceName);
}

