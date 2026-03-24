package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import java.util.Locale;

public final class QuantityMathHelper {
    private static final double EPSILON = 1e-6;

    private QuantityMathHelper() {
    }

    public static boolean compare(double thisValue, String thisUnit, String thisMeasurementType,
                                  double thatValue, String thatUnit, String thatMeasurementType) {
        ensureSameMeasurementType(thisMeasurementType, thatMeasurementType, "compare");
        double convertedThatValue = convert(thatValue, thatUnit, thisUnit, thisMeasurementType);
        return Math.abs(thisValue - convertedThatValue) <= EPSILON;
    }

    public static double convert(double value, String fromUnit, String toUnit, String measurementType) {
        String normalizedMeasurementType = normalizeMeasurementType(measurementType);
        String normalizedFromUnit = normalizeUnit(fromUnit);
        String normalizedToUnit = normalizeUnit(toUnit);

        if (normalizedMeasurementType.equals("LengthUnit")) {
            double baseMeters = toMeters(value, normalizedFromUnit);
            return fromMeters(baseMeters, normalizedToUnit);
        } else if (normalizedMeasurementType.equals("WeightUnit")) {
            double baseKilograms = toKilograms(value, normalizedFromUnit);
            return fromKilograms(baseKilograms, normalizedToUnit);
        } else if (normalizedMeasurementType.equals("VolumeUnit")) {
            double baseLiters = toLiters(value, normalizedFromUnit);
            return fromLiters(baseLiters, normalizedToUnit);
        } else if (normalizedMeasurementType.equals("TemperatureUnit")) {
            double baseCelsius = toCelsius(value, normalizedFromUnit);
            return fromCelsius(baseCelsius, normalizedToUnit);
        } else {
            throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
        }
    }

    public static double add(double thisValue, String thisUnit, String thisMeasurementType,
                             double thatValue, String thatUnit, String thatMeasurementType) {
        ensureSameMeasurementType(thisMeasurementType, thatMeasurementType, "add");
        double convertedThatValue = convert(thatValue, thatUnit, thisUnit, thisMeasurementType);
        return thisValue + convertedThatValue;
    }

    public static double subtract(double thisValue, String thisUnit, String thisMeasurementType,
                                  double thatValue, String thatUnit, String thatMeasurementType) {
        ensureSameMeasurementType(thisMeasurementType, thatMeasurementType, "subtract");
        double convertedThatValue = convert(thatValue, thatUnit, thisUnit, thisMeasurementType);
        return thisValue - convertedThatValue;
    }

    public static double multiply(double thisValue, String thisUnit, String thisMeasurementType,
                                  double thatValue, String thatUnit, String thatMeasurementType) {
        ensureSameMeasurementType(thisMeasurementType, thatMeasurementType, "multiply");
        double convertedThatValue = convert(thatValue, thatUnit, thisUnit, thisMeasurementType);
        return thisValue * convertedThatValue;
    }

    public static double divide(double thisValue, String thisUnit, String thisMeasurementType,
                                double thatValue, String thatUnit, String thatMeasurementType) {
        ensureSameMeasurementType(thisMeasurementType, thatMeasurementType, "divide");
        double convertedThatValue = convert(thatValue, thatUnit, thisUnit, thisMeasurementType);
        if (Math.abs(convertedThatValue) <= EPSILON) {
            throw new ArithmeticException("Divide by zero");
        }
        return thisValue / convertedThatValue;
    }

    public static boolean isValidUnitForMeasurementType(String unit, String measurementType) {
        if (unit == null || measurementType == null) {
            return false;
        }

        try {
            String normalizedMeasurementType = normalizeMeasurementType(measurementType);
            String normalizedUnit = normalizeUnit(unit);

            if (normalizedMeasurementType.equals("LengthUnit")) {
                return isLengthUnit(normalizedUnit);
            } else if (normalizedMeasurementType.equals("WeightUnit")) {
                return isWeightUnit(normalizedUnit);
            } else if (normalizedMeasurementType.equals("VolumeUnit")) {
                return isVolumeUnit(normalizedUnit);
            } else if (normalizedMeasurementType.equals("TemperatureUnit")) {
                return isTemperatureUnit(normalizedUnit);
            } else {
                return false;
            }
        } catch (QuantityMeasurementException exception) {
            return false;
        }
    }

    public static String normalizeMeasurementType(String measurementType) {
        if (measurementType == null || measurementType.trim().isEmpty()) {
            throw new QuantityMeasurementException("Measurement type is required.");
        }

        String normalized = measurementType.trim().toUpperCase(Locale.ROOT);
        if (normalized.equals("LENGTH") || normalized.equals("LENGTHUNIT")) {
            return "LengthUnit";
        }
        if (normalized.equals("WEIGHT") || normalized.equals("WEIGHTUNIT")) {
            return "WeightUnit";
        }
        if (normalized.equals("VOLUME") || normalized.equals("VOLUMEUNIT")) {
            return "VolumeUnit";
        }
        if (normalized.equals("TEMPERATURE") || normalized.equals("TEMPERATUREUNIT")) {
            return "TemperatureUnit";
        }

        throw new QuantityMeasurementException("Invalid measurement type: " + measurementType);
    }

    private static void ensureSameMeasurementType(String thisMeasurementType, String thatMeasurementType,
                                                  String operationName) {
        String thisType = normalizeMeasurementType(thisMeasurementType);
        String thatType = normalizeMeasurementType(thatMeasurementType);
        if (!thisType.equals(thatType)) {
            throw new QuantityMeasurementException(operationName + " Error: Cannot perform arithmetic between different "
                    + "measurement categories: " + thisType + " and " + thatType);
        }
    }

    private static String normalizeUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            throw new QuantityMeasurementException("Unit name is required.");
        }
        return unit.trim().toUpperCase(Locale.ROOT);
    }

    private static double toMeters(double value, String unit) {
        if (unit.equals("INCH") || unit.equals("INCHES")) return value * 0.0254;
        if (unit.equals("FOOT") || unit.equals("FEET")) return value * 0.3048;
        if (unit.equals("YARD") || unit.equals("YARDS")) return value * 0.9144;
        if (unit.equals("CENTIMETER") || unit.equals("CENTIMETERS") || unit.equals("CM")) return value * 0.01;
        if (unit.equals("METER") || unit.equals("METERS") || unit.equals("M")) return value;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double fromMeters(double value, String unit) {
        if (unit.equals("INCH") || unit.equals("INCHES")) return value / 0.0254;
        if (unit.equals("FOOT") || unit.equals("FEET")) return value / 0.3048;
        if (unit.equals("YARD") || unit.equals("YARDS")) return value / 0.9144;
        if (unit.equals("CENTIMETER") || unit.equals("CENTIMETERS") || unit.equals("CM")) return value / 0.01;
        if (unit.equals("METER") || unit.equals("METERS") || unit.equals("M")) return value;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double toKilograms(double value, String unit) {
        if (unit.equals("GRAM") || unit.equals("GRAMS") || unit.equals("G")) return value * 0.001;
        if (unit.equals("KILOGRAM") || unit.equals("KILOGRAMS") || unit.equals("KG")) return value;
        if (unit.equals("POUND") || unit.equals("POUNDS") || unit.equals("LB") || unit.equals("LBS")) return value * 0.45359237;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double fromKilograms(double value, String unit) {
        if (unit.equals("GRAM") || unit.equals("GRAMS") || unit.equals("G")) return value / 0.001;
        if (unit.equals("KILOGRAM") || unit.equals("KILOGRAMS") || unit.equals("KG")) return value;
        if (unit.equals("POUND") || unit.equals("POUNDS") || unit.equals("LB") || unit.equals("LBS")) return value / 0.45359237;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double toLiters(double value, String unit) {
        if (unit.equals("MILLILITER") || unit.equals("MILLILITERS") || unit.equals("MILLILITRE") || unit.equals("MILLILITRES") || unit.equals("ML")) return value * 0.001;
        if (unit.equals("LITER") || unit.equals("LITERS") || unit.equals("LITRE") || unit.equals("LITRES") || unit.equals("L")) return value;
        if (unit.equals("GALLON") || unit.equals("GALLONS")) return value * 3.78541;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double fromLiters(double value, String unit) {
        if (unit.equals("MILLILITER") || unit.equals("MILLILITERS") || unit.equals("MILLILITRE") || unit.equals("MILLILITRES") || unit.equals("ML")) return value / 0.001;
        if (unit.equals("LITER") || unit.equals("LITERS") || unit.equals("LITRE") || unit.equals("LITRES") || unit.equals("L")) return value;
        if (unit.equals("GALLON") || unit.equals("GALLONS")) return value / 3.78541;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double toCelsius(double value, String unit) {
        if (unit.equals("CELSIUS") || unit.equals("C")) return value;
        if (unit.equals("FAHRENHEIT") || unit.equals("F")) return (value - 32.0) * 5.0 / 9.0;
        if (unit.equals("KELVIN") || unit.equals("K")) return value - 273.15;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static double fromCelsius(double value, String unit) {
        if (unit.equals("CELSIUS") || unit.equals("C")) return value;
        if (unit.equals("FAHRENHEIT") || unit.equals("F")) return (value * 9.0 / 5.0) + 32.0;
        if (unit.equals("KELVIN") || unit.equals("K")) return value + 273.15;
        throw new QuantityMeasurementException("Invalid unit name: " + unit + ".");
    }

    private static boolean isLengthUnit(String unit) {
        return unit.equals("INCH") || unit.equals("INCHES") || unit.equals("FOOT") || unit.equals("FEET") ||
               unit.equals("YARD") || unit.equals("YARDS") || unit.equals("CENTIMETER") || unit.equals("CENTIMETERS") ||
               unit.equals("CM") || unit.equals("METER") || unit.equals("METERS") || unit.equals("M");
    }

    private static boolean isWeightUnit(String unit) {
        return unit.equals("GRAM") || unit.equals("GRAMS") || unit.equals("G") ||
               unit.equals("KILOGRAM") || unit.equals("KILOGRAMS") || unit.equals("KG") ||
               unit.equals("POUND") || unit.equals("POUNDS") || unit.equals("LB") || unit.equals("LBS");
    }

    private static boolean isVolumeUnit(String unit) {
        return unit.equals("MILLILITER") || unit.equals("MILLILITERS") || unit.equals("MILLILITRE") ||
               unit.equals("MILLILITRES") || unit.equals("ML") || unit.equals("LITER") ||
               unit.equals("LITERS") || unit.equals("LITRE") || unit.equals("LITRES") ||
               unit.equals("L") || unit.equals("GALLON") || unit.equals("GALLONS");
    }

    private static boolean isTemperatureUnit(String unit) {
        return unit.equals("CELSIUS") || unit.equals("C") ||
               unit.equals("FAHRENHEIT") || unit.equals("F") ||
               unit.equals("KELVIN") || unit.equals("K");
    }
}