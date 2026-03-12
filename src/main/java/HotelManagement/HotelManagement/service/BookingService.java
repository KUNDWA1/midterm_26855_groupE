package HotelManagement.HotelManagement.service;

import HotelManagement.HotelManagement.dto.BookingRequestDto;
import HotelManagement.HotelManagement.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto dto);
    BookingResponseDto getBookingById(Long id);
    List<BookingResponseDto> getAllBookings();
    void cancelBooking(Long id);
}

