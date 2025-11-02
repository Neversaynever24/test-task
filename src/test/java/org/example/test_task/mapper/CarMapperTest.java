package org.example.test_task.mapper;

import org.example.test_task.dto.CarDto;
import org.example.test_task.entity.CarEntity;
import org.example.test_task.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarMapperTest {
    private final CarMapper carMapper = new CarMapper();

    @Test
    void toCarDto_shouldMapEntityToDtoWithVendorModelFormat() {
        CarEntity carEntity = mock(CarEntity.class);
        PersonEntity ownerEntity = mock(PersonEntity.class);

        when(carEntity.getId()).thenReturn(1L);
        when(carEntity.getModel()).thenReturn("bmw-x5");
        when(carEntity.getHorsepower()).thenReturn(300);
        when(carEntity.getOwner()).thenReturn(ownerEntity);
        when(ownerEntity.getId()).thenReturn(100L);

        CarDto result = carMapper.toCarDto(carEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("bmw-x5", result.getModel());
        assertEquals(300, result.getHorsepower());
        assertEquals(100L, result.getOwnerId());
    }
}
