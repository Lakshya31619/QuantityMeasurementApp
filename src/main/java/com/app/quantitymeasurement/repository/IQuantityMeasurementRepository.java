package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findAllByOrderByCreatedAtAsc();
    List<QuantityMeasurementEntity> findAllByOrderByCreatedAtDesc();

    List<QuantityMeasurementEntity> findByOperationTypeOrderByCreatedAtAsc(String operationType);
    List<QuantityMeasurementEntity> findByOperationTypeOrderByCreatedAtDesc(String operationType);

    List<QuantityMeasurementEntity> findByUserEmailOrderByCreatedAtAsc(String userEmail);
    List<QuantityMeasurementEntity> findByUserEmailOrderByCreatedAtDesc(String userEmail);

    List<QuantityMeasurementEntity> findByUserEmailAndOperationTypeOrderByCreatedAtAsc(
            String userEmail, String operationType
    );

    List<QuantityMeasurementEntity> findByUserEmailAndOperationTypeOrderByCreatedAtDesc(
            String userEmail, String operationType
    );
}