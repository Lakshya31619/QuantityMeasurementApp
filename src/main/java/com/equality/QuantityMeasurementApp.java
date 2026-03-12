package com.equality;

import com.equality.controller.QuantityMeasurementController;
import com.equality.dto.QuantityDTO;
import com.equality.repository.QuantityMeasurementCacheRepository;
import com.equality.service.IQuantityMeasurementService;
import com.equality.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    // Method required by the test file
    public static double convert(double value, LengthUnit from, LengthUnit to) {
        Quantity<LengthUnit> quantity = new Quantity<>(value, from);
        Quantity<LengthUnit> result = quantity.convertTo(to);
        return result.getValue();
    }

    public static void main(String[] args) {

        QuantityMeasurementCacheRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO feet = new QuantityDTO(1.0, "FEET");
        QuantityDTO inches = new QuantityDTO(12.0, "INCH");

        controller.performComparison(feet, inches);
        controller.performAddition(feet, inches);
        controller.performConversion(feet, "INCH");
    }
}
