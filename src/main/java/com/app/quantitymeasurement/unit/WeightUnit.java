package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {

    KG(1),
    GRAM(0.001);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}