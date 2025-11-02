package org.example.test_task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
public class CarDto {
    Long id;

    @NotNull
    String model;

    @NotNull
    @Min(value = 1)
    Integer horsepower;

    @NotNull
    Long ownerId;
}
