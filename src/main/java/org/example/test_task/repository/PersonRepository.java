package org.example.test_task.repository;

import org.example.test_task.entity.CarEntity;
import org.example.test_task.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}