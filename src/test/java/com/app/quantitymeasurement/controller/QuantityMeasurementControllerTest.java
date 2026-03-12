package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;

    @Before
    public void setup() {

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(
                        QuantityMeasurementCacheRepository.getInstance());

        controller = new QuantityMeasurementController(service);
    }

    @Test
    public void testPerformComparison() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH");

        controller.performComparison(q1, q2);
    }

    @Test
    public void testPerformAddition() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET");

        controller.performAddition(q1, q2);
    }

    @Test
    public void testPerformConversion() {

        QuantityDTO q = new QuantityDTO(1.0, "FEET");

        controller.performConversion(q, "INCH");
    }
}