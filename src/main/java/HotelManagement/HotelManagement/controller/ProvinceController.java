package HotelManagement.HotelManagement.controller;

import HotelManagement.HotelManagement.dto.ProvinceDto;
import HotelManagement.HotelManagement.service.ProvinceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping
    public ResponseEntity<ProvinceDto> createProvince(@RequestBody ProvinceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.createProvince(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProvinceDto>> getAll() {
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }
}

