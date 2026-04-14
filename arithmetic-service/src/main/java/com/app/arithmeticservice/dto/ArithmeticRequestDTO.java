package com.app.arithmeticservice.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ArithmeticRequestDTO {
    @NotNull private QuantityDTO firstQuantity;
    @NotNull private QuantityDTO secondQuantity;
    private String resultUnit;
}
