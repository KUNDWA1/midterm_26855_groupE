package HotelManagement.HotelManagement.dto;

import lombok.Data;

@Data
public class LocationResponseDto {
    private Long id;
    private String provinceCode;
    private String provinceName;
    private String district;
    private String sector;
    private String cell;
    private String village;
}

