package HotelManagement.HotelManagement.dto;

import HotelManagement.HotelManagement.entity.enums.Gender;
import HotelManagement.HotelManagement.entity.enums.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;

    // Location summary
    private Long locationId;
    private String provinceCode;
    private String provinceName;
    private String district;

    // Profile summary
    private Long profileId;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
}

