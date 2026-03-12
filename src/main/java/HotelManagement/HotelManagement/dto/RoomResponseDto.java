package HotelManagement.HotelManagement.dto;

import HotelManagement.HotelManagement.entity.enums.RoomStatus;
import HotelManagement.HotelManagement.entity.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponseDto {
    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private BigDecimal price;
    private RoomStatus status;

    private Long locationId;
    private String district;
    private String provinceCode;
}

