package com.app.quantitymeasurement.unit;

public interface IMeasurable {

    double toBase(double value);

    double fromBase(double baseValue);
}