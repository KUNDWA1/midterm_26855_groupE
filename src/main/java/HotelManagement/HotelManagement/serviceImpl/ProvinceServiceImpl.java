package HotelManagement.HotelManagement.serviceImpl;

import HotelManagement.HotelManagement.dto.ProvinceDto;
import HotelManagement.HotelManagement.entity.Province;
import HotelManagement.HotelManagement.exception.BadRequestException;
import HotelManagement.HotelManagement.repository.ProvinceRepository;
import HotelManagement.HotelManagement.service.ProvinceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public ProvinceDto createProvince(ProvinceDto dto) {
        if (provinceRepository.existsByProvinceCode(dto.getProvinceCode())) {
            throw new BadRequestException("Province code already exists");
        }
        if (provinceRepository.existsByProvinceName(dto.getProvinceName())) {
            throw new BadRequestException("Province name already exists");
        }
        Province province = new Province();
        province.setProvinceCode(dto.getProvinceCode());
        province.setProvinceName(dto.getProvinceName());
        Province saved = provinceRepository.save(province);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<ProvinceDto> getAllProvinces() {
        return provinceRepository.findAll().stream().map(this::toDto).toList();
    }

    private ProvinceDto toDto(Province province) {
        ProvinceDto dto = new ProvinceDto();
        dto.setId(province.getId());
        dto.setProvinceCode(province.getProvinceCode());
        dto.setProvinceName(province.getProvinceName());
        return dto;
    }
}

