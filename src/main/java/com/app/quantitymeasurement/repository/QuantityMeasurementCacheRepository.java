package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final QuantityMeasurementCacheRepository INSTANCE =
            new QuantityMeasurementCacheRepository();

    private final List<QuantityMeasurementEntity> storage = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        storage.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return storage;
    }

    @Override
    public int getTotalCount() {
        return storage.size();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}