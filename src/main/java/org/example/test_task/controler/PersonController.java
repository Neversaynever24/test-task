package org.example.test_task.controler;

import jakarta.persistence.EntityExistsException;
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
        try {
            return ResponseEntity.ok(personService.createPerson(personDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/personwithcars")
    public ResponseEntity<PersonWithCarDto> getPersonWithCar(
            @RequestParam Long personId
    ) {
        try {
            return ResponseEntity.ok(personService.getPersonWithCars(personId));
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
