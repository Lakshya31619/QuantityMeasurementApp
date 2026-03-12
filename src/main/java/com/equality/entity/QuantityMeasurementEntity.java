package com.equality.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String operation;
    private String operand1;
    private String operand2;
    private String result;
    private boolean error;
    private String errorMessage;

    public QuantityMeasurementEntity(String operation,
                                     String operand1,
                                     String operand2,
                                     String result) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String operation, String errorMessage) {
        this.operation = operation;
        this.errorMessage = errorMessage;
        this.error = true;
    }

    public boolean hasError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}