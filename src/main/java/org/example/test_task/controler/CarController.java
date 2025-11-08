package org.example.test_task.controler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    @PostMapping()
    public ResponseEntity<CarDto> createCar(
            @RequestBody @Valid CarDto carDto
            ) {
        try {
            return ResponseEntity.ok(carService.createCar(carDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
