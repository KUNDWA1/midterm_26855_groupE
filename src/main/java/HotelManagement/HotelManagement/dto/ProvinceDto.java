package HotelManagement.HotelManagement.dto;

import lombok.Data;

/**
 * Simple DTO used to expose Province data without any JPA internals.
 * This keeps the REST layer decoupled from the entity layer.
 */
@Data
public class ProvinceDto {
    private Long id;
    private String provinceCode;
    private String provinceName;
}

