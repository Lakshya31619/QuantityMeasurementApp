package com.equality;
public enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER((0.393701) / 12.0);
    private final double toFeetFactor;
    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }
    public double toFeetFactor() {
        return toFeetFactor;
    }
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}