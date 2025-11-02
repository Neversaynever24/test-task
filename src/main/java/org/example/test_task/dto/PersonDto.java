package org.example.test_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
public class PersonDto {
    Long id;

    @NotNull
    String name;

    @Past
    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull
    LocalDate birthDate;
}
