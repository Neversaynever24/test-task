package org.example.test_task.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatisticsDto {
    Long personCount;
    Long carCount;
    Long uniqueVendorCount;
}
