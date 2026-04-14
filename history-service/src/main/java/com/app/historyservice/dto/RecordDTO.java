package com.app.historyservice.dto;
import lombok.*;
import java.time.LocalDateTime;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RecordDTO {
    private Long id;
    private String operationType;
    private Double firstOperandValue; private String firstMeasurementType; private String firstUnit;
    private Double secondOperandValue; private String secondMeasurementType; private String secondUnit;
    private Double resultOperandValue; private String resultMeasurementType; private String resultUnit;
    private Boolean comparisonResult;
    private Boolean successful;
    private String errorMessage;
    private String userEmail;
    private LocalDateTime createdAt;
}
