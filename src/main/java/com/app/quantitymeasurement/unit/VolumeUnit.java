package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {

    LITER(1),
    ML(0.001);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double base) {
        return base / factor;
    }
}