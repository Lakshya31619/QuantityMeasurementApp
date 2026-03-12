package com.app.quantitymeasurement.unit;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT;

    public double toBase(double value) {
        return value;
    }

    public double fromBase(double base) {
        return base;
    }
}