package HotelManagement.HotelManagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * Request DTO for creating a booking.
 * This demonstrates how we pass only the foreign key IDs instead of nested entities.
 */
@Data
public class BookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long roomId;

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;
}

