package org.example.test_task.service;

import jakarta.persistence.EntityExistsException;
import org.example.test_task.dto.CarDto;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.mapper.CarMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.test_task.dto.StatisticsDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.repository.CarRepository;
import org.example.test_task.repository.PersonRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarService carService;

    @Test
    void createCar_ShouldThrowException_WhenIdIsNotNull() {
        CarDto carDto = CarDto.builder().id(1L).build();

        assertThrows(IllegalArgumentException.class, () -> carService.createCar(carDto));
    }

    @Test
    void createCar_ShouldThrowException_WhenOwnerNotFound() {
        CarDto carDto = CarDto.builder().ownerId(1L).build();

        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityExistsException.class, () -> carService.createCar(carDto));
    }

    @Test
    void createCar_ShouldSuccessfullyCreateCar() {
        CarDto carDto = CarDto.builder().ownerId(1L).build();

        PersonEntity owner = PersonEntity.builder()
                .birthDate(LocalDate.now().minusYears(25))
                .build();

        CarEntity savedCar = CarEntity.builder().id(1L).build();
        CarDto expectedDto = CarDto.builder().id(1L).build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(carRepository.save(any())).thenReturn(savedCar);
        when(carMapper.toCarDto(savedCar)).thenReturn(expectedDto);

        CarDto result = carService.createCar(carDto);

        assertNotNull(result);
    }
}
