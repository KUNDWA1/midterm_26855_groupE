package HotelManagement.HotelManagement.exception;

/**
 * Used when the client sends invalid data (e.g. email already exists).
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

