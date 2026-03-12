package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementService service;

    @Before
    public void setup() {

        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance());
    }

    @Test
    public void testCompareQuantities() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET");
        QuantityDTO q2 = new QuantityDTO(1.0, "FEET");

        boolean result = service.compare(q1, q2);

        Assert.assertTrue(result);
    }

    @Test
    public void testAddition() {

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET");
        QuantityDTO q2 = new QuantityDTO(2.0, "FEET");

        double result = service.add(q1, q2);

        Assert.assertEquals(3.0, result, 0);
    }

    @Test
    public void testConversion() {

        QuantityDTO q = new QuantityDTO(1.0, "FEET");

        double result = service.convert(q, "INCH");

        Assert.assertEquals(12.0, result, 0);
    }
}