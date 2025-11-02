package org.example.test_task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Value
@Builder
public class PersonWithCarDto {
    Long personId;

    String name;

    LocalDate birthDate;

    List<CarDto> cars;
}
