package org.example.test_task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "horse_power", nullable = false)
    private Integer horsepower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity owner;

    public String getVendor() {
        if (model != null && model.contains("-")) {
            return model.split("-")[0].toLowerCase();
        }
        return model != null ? model.toLowerCase() : "";
    }
}
