package org.example.test_task.repository;

import org.example.test_task.entity.CarEntity;
import org.example.test_task.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    @Query("SELECT p FROM PersonEntity p LEFT JOIN FETCH p.cars WHERE p.id = :personId")
    Optional<PersonEntity> findByIdWithCars(@Param("personId") Long personId);
}