package com.app.quantitymeasurement.integrationTests;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.Before;
import org.junit.Test;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;

    @Before
    public void setup() {

        QuantityMeasurementDatabaseRepository repository =
                new QuantityMeasurementDatabaseRepository();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        controller = new QuantityMeasurementController(service);

        repository.deleteAll();
    }

    @Test
    public void testFullApplicationFlow() {

        QuantityDTO feet = new QuantityDTO(1.0, "FEET");
        QuantityDTO inch = new QuantityDTO(12.0, "INCH");

        controller.performComparison(feet, inch);
        controller.performAddition(feet, inch);
        controller.performConversion(feet, "INCH");
    }
}