package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.OperationRequestDTO;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityOperationResultDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementService {
    QuantityOperationResultDTO convert(QuantityDTO source, String targetUnit);

    QuantityOperationResultDTO compare(QuantityDTO firstQuantity, QuantityDTO secondQuantity);

    QuantityOperationResultDTO add(QuantityDTO firstQuantity, QuantityDTO secondQuantity, String resultUnit);

    QuantityOperationResultDTO subtract(QuantityDTO firstQuantity, QuantityDTO secondQuantity, String resultUnit);

    QuantityOperationResultDTO multiply(QuantityDTO firstQuantity, QuantityDTO secondQuantity, String resultUnit);

    QuantityOperationResultDTO divide(QuantityDTO firstQuantity, QuantityDTO secondQuantity, String resultUnit);

    QuantityOperationResultDTO operate(OperationRequestDTO request);

    List<QuantityMeasurementEntity> getMeasurementHistory();

    List<QuantityMeasurementEntity> getMeasurementHistoryByOperation(String operationType);
}