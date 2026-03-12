package HotelManagement.HotelManagement.controller;

import HotelManagement.HotelManagement.dto.UserRequestDto;
import HotelManagement.HotelManagement.dto.UserResponseDto;
import HotelManagement.HotelManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
                                                  @Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Pagination + Sorting endpoint (assessment requirement).
     *
     * Example:
     *   GET /api/users?page=0&size=5&sort=firstName,asc
     *
     * - page, size come from query params
     * - sort field and direction are mapped to a Sort object
     * - Pageable encapsulates both paging and sorting
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponseDto> result = userService.getUsers(pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieve all users from a given province using province code.
     * This uses repository method: findByLocationProvinceProvinceCode
     */
    @GetMapping("/by-province-code/{provinceCode}")
    public ResponseEntity<List<UserResponseDto>> getByProvinceCode(@PathVariable String provinceCode) {
        return ResponseEntity.ok(userService.getUsersByProvinceCode(provinceCode));
    }

    /**
     * Retrieve all users from a given province using province name.
     * This uses repository method: findByLocationProvinceProvinceName
     */
    @GetMapping("/by-province-name/{provinceName}")
    public ResponseEntity<List<UserResponseDto>> getByProvinceName(@PathVariable String provinceName) {
        return ResponseEntity.ok(userService.getUsersByProvinceName(provinceName));
    }
}

