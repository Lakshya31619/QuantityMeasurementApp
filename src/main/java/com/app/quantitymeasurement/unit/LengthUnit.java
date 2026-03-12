package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {

    FEET(12),
    INCH(1);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}