package com.equality.service;

import com.equality.*;
import com.equality.dto.QuantityDTO;
import com.equality.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, String targetUnit) {

        LengthUnit src = LengthUnit.valueOf(source.getUnit());
        LengthUnit tgt = LengthUnit.valueOf(targetUnit);

        Quantity<LengthUnit> q = new Quantity<>(source.getValue(), src);
        Quantity<LengthUnit> result = q.convertTo(tgt);

        return new QuantityDTO(result.getValue(), tgt.name());
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        LengthUnit u1 = LengthUnit.valueOf(q1.getUnit());
        LengthUnit u2 = LengthUnit.valueOf(q2.getUnit());

        Quantity<LengthUnit> a = new Quantity<>(q1.getValue(), u1);
        Quantity<LengthUnit> b = new Quantity<>(q2.getValue(), u2);

        Quantity<LengthUnit> result = a.add(b);

        return new QuantityDTO(result.getValue(), result.getUnit().name());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        LengthUnit u1 = LengthUnit.valueOf(q1.getUnit());
        LengthUnit u2 = LengthUnit.valueOf(q2.getUnit());

        Quantity<LengthUnit> a = new Quantity<>(q1.getValue(), u1);
        Quantity<LengthUnit> b = new Quantity<>(q2.getValue(), u2);

        Quantity<LengthUnit> result = a.subtract(b);

        return new QuantityDTO(result.getValue(), result.getUnit().name());
    }

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {

        LengthUnit u1 = LengthUnit.valueOf(q1.getUnit());
        LengthUnit u2 = LengthUnit.valueOf(q2.getUnit());

        Quantity<LengthUnit> a = new Quantity<>(q1.getValue(), u1);
        Quantity<LengthUnit> b = new Quantity<>(q2.getValue(), u2);

        Quantity<LengthUnit> result = a.divide(b);

        return new QuantityDTO(result.getValue(), result.getUnit().name());
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        LengthUnit u1 = LengthUnit.valueOf(q1.getUnit());
        LengthUnit u2 = LengthUnit.valueOf(q2.getUnit());

        Quantity<LengthUnit> a = new Quantity<>(q1.getValue(), u1);
        Quantity<LengthUnit> b = new Quantity<>(q2.getValue(), u2);

        return a.equals(b);
    }
}