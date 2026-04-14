package com.app.historyservice.repository;

import com.app.historyservice.entity.MeasurementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MeasurementRecordRepository extends JpaRepository<MeasurementRecord, Long> {
    List<MeasurementRecord> findByUserEmailOrderByCreatedAtDesc(String userEmail);
    List<MeasurementRecord> findByUserEmailAndOperationTypeOrderByCreatedAtDesc(String userEmail, String operationType);
}
