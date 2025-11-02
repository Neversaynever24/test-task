package org.example.test_task.mapper;

import org.example.test_task.dto.CarDto;
import org.example.test_task.entity.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDto toCarDto(CarEntity carEntity) {
        return CarDto.builder()
                .id(carEntity.getId())
                .model(carEntity.getModel())
                .horsepower(carEntity.getHorsepower())
                .ownerId(carEntity.getOwner().getId())
                .build();
    }
}
