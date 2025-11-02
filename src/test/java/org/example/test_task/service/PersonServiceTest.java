package org.example.test_task.service;

import jakarta.persistence.EntityExistsException;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.mapper.CarMapper;
import org.example.test_task.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void createPerson_ShouldThrowException_WhenIdIsNotNull() {
        PersonDto inputDto = PersonDto.builder()
                .id(1L)
                .name("John Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> personService.createPerson(inputDto));

        assertEquals("Person id must be empty but is 1", exception.getMessage());
        verify(personRepository, never()).save(any(PersonEntity.class));
    }

    @Test
    void getPersonWithCars_ShouldReturnPersonWithCars_WhenPersonExists() {
        Long personId = 1L;

        CarEntity carEntity1 = CarEntity.builder()
                .id(1L)
                .model("bmw-x5")
                .build();

        CarEntity carEntity2 = CarEntity.builder()
                .id(2L)
                .model("toyota-camry")
                .build();

        PersonEntity personEntity = PersonEntity.builder()
                .id(personId)
                .name("John Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .cars(List.of(carEntity1, carEntity2))
                .build();

        CarDto carDto1 = CarDto.builder()
                .id(1L)
                .model("bmw-x5")
                .build();

        CarDto carDto2 = CarDto.builder()
                .id(2L)
                .model("toyota-camry")
                .build();

        when(personRepository.findByIdWithCars(personId)).thenReturn(Optional.of(personEntity));
        when(carMapper.toCarDto(carEntity1)).thenReturn(carDto1);
        when(carMapper.toCarDto(carEntity2)).thenReturn(carDto2);

        PersonWithCarDto result = personService.getPersonWithCars(personId);

        assertNotNull(result);
        assertEquals(personId, result.getPersonId());
        assertEquals("John Doe", result.getName());
        assertEquals(LocalDate.of(1990, 1, 1), result.getBirthDate());
        assertEquals(2, result.getCars().size());
        assertEquals("bmw-x5", result.getCars().get(0).getModel());
        assertEquals("toyota-camry", result.getCars().get(1).getModel());

        verify(personRepository, times(1)).findByIdWithCars(personId);
        verify(carMapper, times(2)).toCarDto(any(CarEntity.class));
    }

    @Test
    void getPersonWithCars_ShouldThrowException_WhenPersonNotFound() {
        Long personId = 999L;
        when(personRepository.findByIdWithCars(personId)).thenReturn(Optional.empty());

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> personService.getPersonWithCars(personId));

        assertEquals("Person with Cars not found", exception.getMessage());
        verify(personRepository, times(1)).findByIdWithCars(personId);
        verify(carMapper, never()).toCarDto(any(CarEntity.class));
    }
}