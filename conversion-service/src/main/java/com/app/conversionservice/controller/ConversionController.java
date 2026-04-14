package com.app.conversionservice.controller;

import com.app.conversionservice.dto.*;
import com.app.conversionservice.exception.ConversionException;
import com.app.conversionservice.util.QuantityMathHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

    @PostMapping("/convert")
    public ResponseEntity<ConversionResultDTO> convert(@Valid @RequestBody ConversionRequestDTO req) {
        QuantityDTO src = req.getSourceQuantity();
        String targetUnit = req.getTargetUnit().trim().toUpperCase();
        if (!QuantityMathHelper.isValidUnit(targetUnit, src.getMeasurementType()))
            throw new ConversionException("Invalid target unit: " + targetUnit);

        double result = QuantityMathHelper.convert(src.getValue(), src.getUnitName(), targetUnit, src.getMeasurementType());
        QuantityDTO resultQty = QuantityDTO.builder().value(result).measurementType(src.getMeasurementType()).unitName(targetUnit).build();
        return ResponseEntity.ok(ConversionResultDTO.builder()
                .operationType("CONVERT").sourceQuantity(src).resultQuantity(resultQty).successful(true).build());
    }

    @PostMapping("/compare")
    public ResponseEntity<ConversionResultDTO> compare(@Valid @RequestBody CompareRequestDTO req) {
        QuantityDTO q1 = req.getFirstQuantity(), q2 = req.getSecondQuantity();
        boolean result = QuantityMathHelper.compare(q1.getValue(), q1.getUnitName(), q1.getMeasurementType(),
                q2.getValue(), q2.getUnitName(), q2.getMeasurementType());
        return ResponseEntity.ok(ConversionResultDTO.builder()
                .operationType("COMPARE").sourceQuantity(q1).comparisonResult(result).successful(true).build());
    }
}
