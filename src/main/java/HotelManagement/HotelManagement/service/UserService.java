package HotelManagement.HotelManagement.service;

import HotelManagement.HotelManagement.dto.UserRequestDto;
import HotelManagement.HotelManagement.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);

    /**
     * Pagination + sorting are handled via Pageable parameter.
     */
    Page<UserResponseDto> getUsers(Pageable pageable);

    /**
     * Province filtering requirement.
     */
    List<UserResponseDto> getUsersByProvinceCode(String provinceCode);
    List<UserResponseDto> getUsersByProvinceName(String provinceName);
}

