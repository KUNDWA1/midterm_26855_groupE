package HotelManagement.HotelManagement.exception;

/**
 * Thrown when a requested entity (User, Room, etc.) does not exist.
 * This is handled centrally in GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

