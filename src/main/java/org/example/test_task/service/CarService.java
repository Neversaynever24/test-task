package org.example.test_task.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.mapper.CarMapper;
import org.example.test_task.repository.CarRepository;
import org.example.test_task.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    public final PersonRepository personRepository;
    public final CarMapper carMapper;

    @Transactional
    public CarDto createCar(@NonNull CarDto carToCreateDto){
        if (carToCreateDto.getId() != null) { // проверка на то чтобы carId не передавался в запросе
            throw new IllegalArgumentException("CarId must be empty but is " + carToCreateDto.getId());
        }

        PersonEntity owner = personRepository.findById(carToCreateDto.getOwnerId()).orElseThrow( // находим владельца по id
                () -> new EntityNotFoundException("Owner not found")
        );

        Period age = Period.between(owner.getBirthDate(), LocalDate.now()); // проверка на возраст владельца (< 18)
        if (age.getYears() < 18) {
            throw new IllegalArgumentException("Person age must be at least 18 years");
        }

        var carEntityToSave = CarEntity.builder() // собираем entity
                .model(carToCreateDto.getModel())
                .horsepower(carToCreateDto.getHorsepower())
                .owner(owner)
                .build();

        var carEntitySaved = carRepository.save(carEntityToSave); // возвращаем сохранненый entity и мапим в dto
        return carMapper.toCarDto(carEntitySaved);
    }
}
