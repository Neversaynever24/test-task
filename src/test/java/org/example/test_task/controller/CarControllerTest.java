package org.example.test_task.controller;

import org.example.test_task.controler.CarController;
import org.example.test_task.dto.CarDto;
import org.example.test_task.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void createCar_ShouldReturnOk_WhenValidRequest() {
        CarDto requestCarDto = CarDto.builder()
                .model("bmw-x5")
                .horsepower(500)
                .ownerId(1L)
                .build();

        CarDto responseCarDto = CarDto.builder()
                .id(1L)
                .model("bmw-x5")
                .horsepower(500)
                .ownerId(1L)
                .build();

        when(carService.createCar(any(CarDto.class))).thenReturn(responseCarDto);

        ResponseEntity<CarDto> response = carController.createCar(requestCarDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("bmw-x5", response.getBody().getModel());
        assertEquals(500, response.getBody().getHorsepower());
        assertEquals(1L, response.getBody().getOwnerId());
    }

    @Test
    void createCar_ShouldReturnBadRequest_WhenIllegalArgumentException() {
        CarDto invalidCarDto = CarDto.builder()
                .model("bmw-x5")
                .horsepower(500)
                .ownerId(1L)
                .build();

        when(carService.createCar(any(CarDto.class)))
                .thenThrow(new IllegalArgumentException("Invalid car data"));

        ResponseEntity<CarDto> response = carController.createCar(invalidCarDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createCar_ShouldReturnBadRequest_WhenNullModel() {
        CarDto invalidCarDto = CarDto.builder()
                .model(null)
                .horsepower(500)
                .ownerId(1L)
                .build();

        when(carService.createCar(any(CarDto.class)))
                .thenThrow(new IllegalArgumentException("Model cannot be null"));

        ResponseEntity<CarDto> response = carController.createCar(invalidCarDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createCar_ShouldReturnBadRequest_WhenInvalidHorsepower() {
        CarDto invalidCarDto = CarDto.builder()
                .model("bmw-x5")
                .horsepower(0)
                .ownerId(1L)
                .build();

        when(carService.createCar(any(CarDto.class)))
                .thenThrow(new IllegalArgumentException("Horsepower must be at least 1"));

        ResponseEntity<CarDto> response = carController.createCar(invalidCarDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void createCar_ShouldHandleServiceExceptions() {
        CarDto carDto = CarDto.builder()
                .model("bmw-x5")
                .horsepower(500)
                .ownerId(1L)
                .build();

        when(carService.createCar(any(CarDto.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(RuntimeException.class, () -> carController.createCar(carDto));
    }
}
