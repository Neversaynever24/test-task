package org.example.test_task.controler;

import lombok.RequiredArgsConstructor;
import org.example.test_task.dto.CarDto;
import org.example.test_task.dto.PersonDto;
import org.example.test_task.dto.PersonWithCarDto;
import org.example.test_task.service.CarService;
import org.example.test_task.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<PersonDto> createCar(
            @RequestBody PersonDto personDto
    ) {
        return ResponseEntity.ok(personService.createPerson(personDto));
    }

    @GetMapping("/personwithcars")
    public ResponseEntity<PersonWithCarDto> getPersonWithCar(
            @RequestParam Long personId
    ) {
        return ResponseEntity.ok(personService.getPersonWithCars(personId));
    }
}
