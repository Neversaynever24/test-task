package org.example.test_task.controler;

import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.CarDto;
import org.example.test_task.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @PostMapping()
    public ResponseEntity<CarDto> createCar(
            @RequestBody CarDto carDto
            ) {
        return ResponseEntity.ok(carService.createCar());
    }
}
