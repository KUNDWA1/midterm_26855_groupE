package HotelManagement.HotelManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request DTO for creating a Location together with its Province reference.
 * This is used to implement the "Location Saving" requirement.
 */
@Data
public class LocationRequestDto {

    @NotBlank
    private String provinceCode;

    @NotBlank
    private String district;

    @NotBlank
    private String sector;

    @NotBlank
    private String cell;

    @NotBlank
    private String village;
}

