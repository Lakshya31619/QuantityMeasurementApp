package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    @Before
    public void setup() {
        repository = new QuantityMeasurementDatabaseRepository();
        repository.deleteAll();
    }

    @Test
    public void testSaveEntity() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperationType("COMPARE");
        entity.setMeasurementType("LENGTH");
        entity.setValue1(1);
        entity.setUnit1("FEET");
        entity.setValue2(12);
        entity.setUnit2("INCH");
        entity.setResult(1);
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        repository.save(entity);

        int count = repository.getTotalCount();

        Assert.assertEquals(1, count);
    }

    @Test
    public void testRetrieveAllMeasurements() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setOperationType("ADD");
        entity.setMeasurementType("LENGTH");
        entity.setValue1(1);
        entity.setUnit1("FEET");
        entity.setValue2(12);
        entity.setUnit2("INCH");
        entity.setResult(2);
        entity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        repository.save(entity);

        List<QuantityMeasurementEntity> list =
                repository.getAllMeasurements();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void testDeleteAll() {

        repository.deleteAll();

        int count = repository.getTotalCount();

        Assert.assertEquals(0, count);
    }
}