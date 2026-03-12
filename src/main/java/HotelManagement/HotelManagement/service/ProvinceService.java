package HotelManagement.HotelManagement.service;

import HotelManagement.HotelManagement.dto.ProvinceDto;

import java.util.List;

public interface ProvinceService {
    ProvinceDto createProvince(ProvinceDto dto);
    List<ProvinceDto> getAllProvinces();
}

