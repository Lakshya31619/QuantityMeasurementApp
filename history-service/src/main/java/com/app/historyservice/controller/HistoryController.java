package com.app.historyservice.controller;

import com.app.historyservice.dto.*;
import com.app.historyservice.entity.MeasurementRecord;
import com.app.historyservice.repository.MeasurementRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final MeasurementRecordRepository repository;

    @PostMapping("/save")
    public ResponseEntity<RecordDTO> save(@RequestBody SaveRecordRequest req) {
        // Always read email from JWT — never trust the request body for this
        String email = currentEmail();
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        MeasurementRecord r = new MeasurementRecord();
        r.setOperationType(req.getOperationType());
        r.setFirstOperandValue(req.getFirstOperandValue());
        r.setFirstMeasurementType(req.getFirstMeasurementType());
        r.setFirstUnit(req.getFirstUnit());
        r.setSecondOperandValue(req.getSecondOperandValue());
        r.setSecondMeasurementType(req.getSecondMeasurementType());
        r.setSecondUnit(req.getSecondUnit());
        r.setResultOperandValue(req.getResultOperandValue());
        r.setResultMeasurementType(req.getResultMeasurementType());
        r.setResultUnit(req.getResultUnit());
        r.setComparisonResult(req.getComparisonResult());
        r.setSuccessful(req.getSuccessful() != null ? req.getSuccessful() : true);
        r.setErrorMessage(req.getErrorMessage());
        r.setUserEmail(email);  // set from JWT, not from request body
        return ResponseEntity.ok(toDTO(repository.save(r)));
    }

    @GetMapping
    public ResponseEntity<List<RecordDTO>> getHistory() {
        String email = currentEmail();
        if (email == null) return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(
            repository.findByUserEmailOrderByCreatedAtDesc(email)
                .stream().map(this::toDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/operation/{operationType}")
    public ResponseEntity<List<RecordDTO>> getHistoryByOperation(@PathVariable String operationType) {
        String email = currentEmail();
        if (email == null) return ResponseEntity.ok(List.of());
        String op = operationType.toUpperCase(Locale.ROOT);
        return ResponseEntity.ok(
            repository.findByUserEmailAndOperationTypeOrderByCreatedAtDesc(email, op)
                .stream().map(this::toDTO).collect(Collectors.toList())
        );
    }

    private String currentEmail() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) return null;
            return auth.getPrincipal().toString();
        } catch (Exception e) { return null; }
    }

    private RecordDTO toDTO(MeasurementRecord r) {
        return RecordDTO.builder()
                .id(r.getId()).operationType(r.getOperationType())
                .firstOperandValue(r.getFirstOperandValue()).firstMeasurementType(r.getFirstMeasurementType()).firstUnit(r.getFirstUnit())
                .secondOperandValue(r.getSecondOperandValue()).secondMeasurementType(r.getSecondMeasurementType()).secondUnit(r.getSecondUnit())
                .resultOperandValue(r.getResultOperandValue()).resultMeasurementType(r.getResultMeasurementType()).resultUnit(r.getResultUnit())
                .comparisonResult(r.getComparisonResult()).successful(r.getSuccessful()).errorMessage(r.getErrorMessage())
                .userEmail(r.getUserEmail()).createdAt(r.getCreatedAt()).build();
    }
}