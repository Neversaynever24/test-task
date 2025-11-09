package org.example.test_task.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.entity.PersonEntity;
import org.example.test_task.mapper.CarMapper;
import org.example.test_task.mapper.PersonMapper;
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
    private final PersonMapper personMapper;

    @Transactional
    public PersonDto createPerson(PersonDto personToCreate) {
        if (personToCreate.getId() != null) { // проверка на то чтобы person id не передавался в запросе
            throw new IllegalArgumentException("Person id must be empty but is " + personToCreate.getId());
        }
        log.info("Called createPerson in PersonService");

        var personEntityToSave = PersonEntity.builder() // собираем entity
                .name(personToCreate.getName())
                .birthDate(personToCreate.getBirthDate())
                .build();

        var savedPersonEntity = personRepository.save(personEntityToSave); // возвращаем сохранненый entity и мапим в dto
        return personMapper.toPersonDto(savedPersonEntity);
    }

    @Transactional(readOnly = true)
    public PersonWithCarDto getPersonWithCars(Long personId) {
        PersonEntity personEntity = personRepository.findByIdWithCars(personId) // находим владельца с со списком машин во владении
                .orElseThrow(() -> new EntityNotFoundException("Person with Cars not found"));

        List<CarDto> carDtos = personEntity.getCars().stream() // мапим entity в список dto
                .map(carMapper::toCarDto)
                .toList();

        return PersonWithCarDto.builder() // возращаем человека по заданному id со списком машин
                .personId(personEntity.getId())
                .name(personEntity.getName())
                .birthDate(personEntity.getBirthDate())
                .cars(carDtos)
                .build();
    }
}
