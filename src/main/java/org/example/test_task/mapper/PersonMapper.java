package org.example.test_task.mapper;

import org.example.test_task.dto.PersonDto;
import org.example.test_task.entity.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonDto toPersonDto(PersonEntity personEntity) {
        return PersonDto.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .birthDate(personEntity.getBirthDate())
                .build();
    }
}
