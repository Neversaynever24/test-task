package org.example.test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
public class CarDto {
    Long id;
    String model;
    int horsepower;
    Long ownerId;
}
