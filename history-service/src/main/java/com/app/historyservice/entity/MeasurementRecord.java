package com.app.historyservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_entity")
public class MeasurementRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name="operation_type", nullable=false, length=32) private String operationType;
    @Column(name="first_operand_value") private Double firstOperandValue;
    @Column(name="first_measurement_type", length=32) private String firstMeasurementType;
    @Column(name="first_unit", length=32) private String firstUnit;
    @Column(name="second_operand_value") private Double secondOperandValue;
    @Column(name="second_measurement_type", length=32) private String secondMeasurementType;
    @Column(name="second_unit", length=32) private String secondUnit;
    @Column(name="result_operand_value") private Double resultOperandValue;
    @Column(name="result_measurement_type", length=32) private String resultMeasurementType;
    @Column(name="result_unit", length=32) private String resultUnit;
    @Column(name="comparison_result") private Boolean comparisonResult;
    @Column(name="error_message", length=1000) private String errorMessage;
    @Column(name="successful", nullable=false) private Boolean successful;
    @Column(name="user_email") private String userEmail;
    @CreationTimestamp @Column(name="created_at", updatable=false) private LocalDateTime createdAt;

    public Long getId() { return id; }
    public String getOperationType() { return operationType; }
    public void setOperationType(String v) { operationType = v; }
    public Double getFirstOperandValue() { return firstOperandValue; }
    public void setFirstOperandValue(Double v) { firstOperandValue = v; }
    public String getFirstMeasurementType() { return firstMeasurementType; }
    public void setFirstMeasurementType(String v) { firstMeasurementType = v; }
    public String getFirstUnit() { return firstUnit; }
    public void setFirstUnit(String v) { firstUnit = v; }
    public Double getSecondOperandValue() { return secondOperandValue; }
    public void setSecondOperandValue(Double v) { secondOperandValue = v; }
    public String getSecondMeasurementType() { return secondMeasurementType; }
    public void setSecondMeasurementType(String v) { secondMeasurementType = v; }
    public String getSecondUnit() { return secondUnit; }
    public void setSecondUnit(String v) { secondUnit = v; }
    public Double getResultOperandValue() { return resultOperandValue; }
    public void setResultOperandValue(Double v) { resultOperandValue = v; }
    public String getResultMeasurementType() { return resultMeasurementType; }
    public void setResultMeasurementType(String v) { resultMeasurementType = v; }
    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String v) { resultUnit = v; }
    public Boolean getComparisonResult() { return comparisonResult; }
    public void setComparisonResult(Boolean v) { comparisonResult = v; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String v) { errorMessage = v; }
    public Boolean getSuccessful() { return successful; }
    public void setSuccessful(Boolean v) { successful = v; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String v) { userEmail = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
