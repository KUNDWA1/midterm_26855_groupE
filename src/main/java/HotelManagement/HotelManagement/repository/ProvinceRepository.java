package HotelManagement.HotelManagement.repository;

import HotelManagement.HotelManagement.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByProvinceCode(String provinceCode);
    Optional<Province> findByProvinceName(String provinceName);
    boolean existsByProvinceCode(String provinceCode);
    boolean existsByProvinceName(String provinceName);
}

