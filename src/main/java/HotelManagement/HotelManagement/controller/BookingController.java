package HotelManagement.HotelManagement.controller;

import HotelManagement.HotelManagement.dto.BookingRequestDto;
import HotelManagement.HotelManagement.dto.BookingResponseDto;
import HotelManagement.HotelManagement.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> create(@Valid @RequestBody BookingRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAll() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}

