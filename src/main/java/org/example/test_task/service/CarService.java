package org.example.test_task.service;

import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.CarDto;
import org.example.test_task.repository.CarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public CarDto createCar() {
        return null;
    }
}
