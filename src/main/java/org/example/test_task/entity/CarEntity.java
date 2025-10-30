package org.example.test_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cars")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private int horsepower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonEntity owner;
}
