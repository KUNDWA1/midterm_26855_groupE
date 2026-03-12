package HotelManagement.HotelManagement.dto;

import HotelManagement.HotelManagement.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponseDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;

    private Long userId;
    private String userFullName;

    private Long roomId;
    private String roomNumber;
}

