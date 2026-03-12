package HotelManagement.HotelManagement.dto;

import HotelManagement.HotelManagement.entity.enums.Gender;
import HotelManagement.HotelManagement.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * Request DTO for creating a User + UserProfile.
 * Note how we include both account data and profile data here.
 */
@Data
public class UserRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role;

    /**
     * Location foreign key (we avoid sending nested entities).
     */
    @NotNull
    private Long locationId;

    // Profile fields
    @NotBlank
    private String phoneNumber;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;
}

