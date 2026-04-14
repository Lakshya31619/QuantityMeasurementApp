package com.app.conversionservice.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConversionResultDTO {
    private String operationType;
    private QuantityDTO sourceQuantity;
    private QuantityDTO resultQuantity;
    private Boolean comparisonResult;
    private boolean successful;
    private String errorMessage;
}
