package org.example.test_task.service;

import org.example.test_task.dto.StatisticsDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.repository.CarRepository;
import org.example.test_task.repository.PersonRepository;
import org.example.test_task.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    void clearAll_ShouldDeleteAllDataAndReturnMessage() {
        String result = adminService.clearAll();

        verify(carRepository, times(1)).deleteAllInBatch();
        verify(personRepository, times(1)).deleteAllInBatch();
        assertEquals("All data cleared!", result);
    }

    @Test
    void getStatistics_ShouldReturnCorrectStatistics() {
        when(personRepository.count()).thenReturn(5L);
        when(carRepository.count()).thenReturn(10L);

        List<CarEntity> mockCars = Arrays.asList(
                createCarEntity("Toyota-Camry"),
                createCarEntity("Honda-Civic"),
                createCarEntity("Toyota-Corolla"),
                createCarEntity("BMW-X5"),
                createCarEntity("Honda-Accord")
        );
        when(carRepository.findAll()).thenReturn(mockCars);

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(5L, result.getPersonCount());
        assertEquals(10L, result.getCarCount());
        assertEquals(3L, result.getUniqueVendorCount());

        verify(personRepository, times(1)).count();
        verify(carRepository, times(1)).count();
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getStatistics_WithEmptyData_ShouldReturnZeroCounts() {
        when(personRepository.count()).thenReturn(0L);
        when(carRepository.count()).thenReturn(0L);
        when(carRepository.findAll()).thenReturn(List.of());

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(0L, result.getPersonCount());
        assertEquals(0L, result.getCarCount());
        assertEquals(0L, result.getUniqueVendorCount());
    }

    @Test
    void getStatistics_WithSingleCar_ShouldReturnOneUniqueVendor() {
        when(personRepository.count()).thenReturn(1L);
        when(carRepository.count()).thenReturn(1L);

        CarEntity singleCar = createCarEntity("Mercedes-E300");
        when(carRepository.findAll()).thenReturn(List.of(singleCar));

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(1L, result.getPersonCount());
        assertEquals(1L, result.getCarCount());
        assertEquals(1L, result.getUniqueVendorCount());
    }

    @Test
    void getStatistics_WithAllSameVendors_ShouldReturnOneUniqueVendor() {
        when(personRepository.count()).thenReturn(3L);
        when(carRepository.count()).thenReturn(3L);

        List<CarEntity> sameVendorCars = Arrays.asList(
                createCarEntity("Ford-Focus"),
                createCarEntity("Ford-Mustang"),
                createCarEntity("Ford-Explorer")
        );
        when(carRepository.findAll()).thenReturn(sameVendorCars);

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(3L, result.getPersonCount());
        assertEquals(3L, result.getCarCount());
        assertEquals(1L, result.getUniqueVendorCount());
    }

    @Test
    void getStatistics_WithDifferentVendorsAndModels_ShouldCountUniqueVendors() {
        when(personRepository.count()).thenReturn(6L);
        when(carRepository.count()).thenReturn(6L);

        List<CarEntity> cars = Arrays.asList(
                createCarEntity("BMW-X3"),
                createCarEntity("BMW-X5"),
                createCarEntity("Audi-A4"),
                createCarEntity("Audi-Q7"),
                createCarEntity("Tesla-Model3"),
                createCarEntity("Toyota-Camry")
        );
        when(carRepository.findAll()).thenReturn(cars);

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(6L, result.getPersonCount());
        assertEquals(6L, result.getCarCount());
        assertEquals(4L, result.getUniqueVendorCount());
    }

    @Test
    void getStatistics_WithVendorWithoutDash_ShouldHandleCorrectly() {
        when(personRepository.count()).thenReturn(2L);
        when(carRepository.count()).thenReturn(2L);

        List<CarEntity> cars = Arrays.asList(
                createCarEntity("Tesla"),
                createCarEntity("BMW-X1")
        );
        when(carRepository.findAll()).thenReturn(cars);

        StatisticsDto result = adminService.getStatistics();

        assertNotNull(result);
        assertEquals(2L, result.getPersonCount());
        assertEquals(2L, result.getCarCount());
        assertEquals(2L, result.getUniqueVendorCount());
    }

    private CarEntity createCarEntity(String model) {
        CarEntity car = new CarEntity();
        car.setModel(model);
        return car;
    }
}