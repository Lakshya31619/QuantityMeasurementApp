package com.app.conversionservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConversionRequestDTO {
    @NotNull private QuantityDTO sourceQuantity;
    @NotNull private String targetUnit;
}
