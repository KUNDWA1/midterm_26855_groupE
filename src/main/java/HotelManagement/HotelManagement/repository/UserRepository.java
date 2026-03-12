package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * existBy() requirement:
     * Spring Data derives SQL like:
     * SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)
     */
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    /**
     * Province filtering requirement:
     * The property path navigates relationships:
     * User -> location -> province -> provinceCode
     */
    List<User> findByLocationProvinceProvinceCode(String provinceCode);

    List<User> findByLocationProvinceProvinceName(String provinceName);
}

