package com.app.arithmeticservice.controller;

import com.app.arithmeticservice.dto.*;
import com.app.arithmeticservice.exception.ArithmeticServiceException;
import com.app.arithmeticservice.util.QuantityMathHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@RestController
@RequestMapping("/api/arithmetic")
public class ArithmeticController {

    @PostMapping("/add")
    public ResponseEntity<ArithmeticResultDTO> add(@Valid @RequestBody ArithmeticRequestDTO req) {
        return ResponseEntity.ok(perform("ADD", req));
    }

    @PostMapping("/subtract")
    public ResponseEntity<ArithmeticResultDTO> subtract(@Valid @RequestBody ArithmeticRequestDTO req) {
        return ResponseEntity.ok(perform("SUBTRACT", req));
    }

    @PostMapping("/multiply")
    public ResponseEntity<ArithmeticResultDTO> multiply(@Valid @RequestBody ArithmeticRequestDTO req) {
        return ResponseEntity.ok(perform("MULTIPLY", req));
    }

    @PostMapping("/divide")
    public ResponseEntity<ArithmeticResultDTO> divide(@Valid @RequestBody ArithmeticRequestDTO req) {
        return ResponseEntity.ok(perform("DIVIDE", req));
    }

    private ArithmeticResultDTO perform(String op, ArithmeticRequestDTO req) {
        QuantityDTO q1 = req.getFirstQuantity(), q2 = req.getSecondQuantity();
        String resultUnit = (req.getResultUnit() == null || req.getResultUnit().isBlank())
                ? q1.getUnitName().toUpperCase(Locale.ROOT)
                : req.getResultUnit().trim().toUpperCase(Locale.ROOT);

        double raw;
        switch (op) {
            case "ADD":      raw = QuantityMathHelper.add(q1.getValue(), q1.getUnitName(), q1.getMeasurementType(), q2.getValue(), q2.getUnitName(), q2.getMeasurementType()); break;
            case "SUBTRACT": raw = QuantityMathHelper.subtract(q1.getValue(), q1.getUnitName(), q1.getMeasurementType(), q2.getValue(), q2.getUnitName(), q2.getMeasurementType()); break;
            case "MULTIPLY": raw = QuantityMathHelper.multiply(q1.getValue(), q1.getUnitName(), q1.getMeasurementType(), q2.getValue(), q2.getUnitName(), q2.getMeasurementType()); break;
            case "DIVIDE":   raw = QuantityMathHelper.divide(q1.getValue(), q1.getUnitName(), q1.getMeasurementType(), q2.getValue(), q2.getUnitName(), q2.getMeasurementType()); break;
            default: throw new ArithmeticServiceException("Unknown operation: " + op);
        }

        double converted = QuantityMathHelper.convertFromBase(raw, resultUnit, q1.getMeasurementType());
        QuantityDTO result = QuantityDTO.builder().value(converted).measurementType(q1.getMeasurementType()).unitName(resultUnit).build();

        return ArithmeticResultDTO.builder()
                .operationType(op).firstQuantity(q1).secondQuantity(q2).resultQuantity(result).successful(true).build();
    }
}
