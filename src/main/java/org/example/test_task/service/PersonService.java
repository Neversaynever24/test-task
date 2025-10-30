package org.example.test_task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public PersonDto createPerson(PersonDto personToCreate) {
        if (personToCreate.getId() != null) {
            throw new IllegalArgumentException("Person id must be empty but is " + personToCreate.getId());
        }
        log.info("Called createPerson in PersonService");

        var personEntityToSave = PersonEntity.builder()
                .name(personToCreate.getName())
                .birthDate(personToCreate.getBirthDate())
                .build();

        personRepository.save(personEntityToSave);
        return toDomainPersonDto(personEntityToSave);
    }

    private PersonDto toDomainPersonDto(PersonEntity personEntity) {
        return PersonDto.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .birthDate(personEntity.getBirthDate())
                .build();
    }
}
