package com.app.conversionservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CompareRequestDTO {
    @NotNull private QuantityDTO firstQuantity;
    @NotNull private QuantityDTO secondQuantity;
}
