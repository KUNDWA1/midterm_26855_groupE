package HotelManagement.HotelManagement.serviceImpl;

import HotelManagement.HotelManagement.dto.UserRequestDto;
import HotelManagement.HotelManagement.dto.UserResponseDto;
import HotelManagement.HotelManagement.entity.Location;
import HotelManagement.HotelManagement.entity.User;
import HotelManagement.HotelManagement.entity.UserProfile;
import HotelManagement.HotelManagement.exception.BadRequestException;
import HotelManagement.HotelManagement.exception.ResourceNotFoundException;
import HotelManagement.HotelManagement.repository.LocationRepository;
import HotelManagement.HotelManagement.repository.UserProfileRepository;
import HotelManagement.HotelManagement.repository.UserRepository;
import HotelManagement.HotelManagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserRepository userRepository,
                           LocationRepository locationRepository,
                           UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * existBy() requirement:
     * - Before creating a user we call userRepository.existsByEmail(email)
     * - Under the hood Spring Data generates a SQL EXISTS query.
     */
    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("User with email already exists");
        }

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + dto.getLocationId()));

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        // In real production system we would hash the password (e.g. BCrypt).
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setLocation(location);

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());
        user.setUserProfile(profile);

        User saved = userRepository.save(user);
        return toDto(saved);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return toDto(user);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + dto.getLocationId()));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        user.setLocation(location);

        UserProfile profile = user.getUserProfile();
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());

        return toDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    /**
     * Pagination + Sorting logic (assessment requirement):
     * - The Pageable parameter contains pageNumber, pageSize and Sort information.
     * - Spring Data converts this into SQL: LIMIT/OFFSET + ORDER BY.
     * - This avoids loading all rows into memory and improves performance.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * Province filtering logic (assessment requirement):
     * - We use nested property paths in method name:
     *   findByLocationProvinceProvinceCode
     * - Spring Data joins users -> locations -> provinces and applies WHERE province_code = ?
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersByProvinceCode(String provinceCode) {
        return userRepository.findByLocationProvinceProvinceCode(provinceCode)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsersByProvinceName(String provinceName) {
        return userRepository.findByLocationProvinceProvinceName(provinceName)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        if (user.getLocation() != null) {
            dto.setLocationId(user.getLocation().getId());
            dto.setDistrict(user.getLocation().getDistrict());
            dto.setProvinceCode(user.getLocation().getProvince().getProvinceCode());
            dto.setProvinceName(user.getLocation().getProvince().getProvinceName());
        }

        if (user.getUserProfile() != null) {
            dto.setProfileId(user.getUserProfile().getId());
            dto.setPhoneNumber(user.getUserProfile().getPhoneNumber());
            dto.setGender(user.getUserProfile().getGender());
            dto.setDateOfBirth(user.getUserProfile().getDateOfBirth());
        }

        return dto;
    }
}

