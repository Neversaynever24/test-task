package org.example.test_task.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.mapper.CarMapper;
import org.example.test_task.repository.CarRepository;
import org.example.test_task.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final CarMapper carMapper;

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
        return convertToPersonDto(personEntityToSave);
    }

    public PersonWithCarDto getPersonWithCars(Long personId) {
        PersonEntity personEntity = personRepository.findByIdWithCars(personId)
                .orElseThrow(() -> new EntityExistsException("Person with Cars not found"));

        List<CarDto> carDtos = personEntity.getCars().stream()
                .map(carMapper::toCarDto)
                .toList();

        return PersonWithCarDto.builder()
                .personId(personEntity.getId())
                .name(personEntity.getName())
                .birthDate(personEntity.getBirthDate())
                .cars(carDtos)
                .build();
    }

    private PersonDto convertToPersonDto(PersonEntity personEntity) {
        return PersonDto.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .birthDate(personEntity.getBirthDate())
                .build();
    }
}
