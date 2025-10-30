package org.example.test_task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "persons")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;
}
