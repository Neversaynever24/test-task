package org.example.test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class PersonDto {
    Long id;
    String name;
    Date birthday;
}
