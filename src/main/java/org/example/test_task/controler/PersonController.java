package org.example.test_task.controler;

import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<PersonDto> createCar(
            @RequestBody PersonDto personDto
    ) {
        return ResponseEntity.ok(personService.createPerson(personDto));
    }
}
