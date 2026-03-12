package HotelManagement.HotelManagement.dto;

import HotelManagement.HotelManagement.entity.enums.RoomStatus;
import HotelManagement.HotelManagement.entity.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequestDto {
    @NotBlank
    private String roomNumber;

    @NotNull
    private RoomType roomType;

    @NotNull
    private BigDecimal price;

    @NotNull
    private RoomStatus status;

    @NotNull
    private Long locationId;
}

