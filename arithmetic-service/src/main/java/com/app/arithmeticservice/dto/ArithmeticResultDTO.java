package com.app.arithmeticservice.dto;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ArithmeticResultDTO {
    private String operationType;
    private QuantityDTO firstQuantity;
    private QuantityDTO secondQuantity;
    private QuantityDTO resultQuantity;
    private boolean successful;
    private String errorMessage;
}
