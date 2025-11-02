package org.example.test_task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_task.dto.StatisticsDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.repository.CarRepository;
import org.example.test_task.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    public String clearAll() {
        carRepository.deleteAllInBatch();
        personRepository.deleteAllInBatch();
        return "All data cleared!";
    }

    public StatisticsDto getStatistics() {
        Long personCount = personRepository.count();
        Long carCount = carRepository.count();

        List<CarEntity> allCars = carRepository.findAll();
        Long uniqueVendorCount = allCars.stream()
                .map(CarEntity::getVendor)
                .distinct()
                .count();

        return StatisticsDto.builder()
                .personCount(personCount)
                .carCount(carCount)
                .uniqueVendorCount(uniqueVendorCount)
                .build();
    }
}
