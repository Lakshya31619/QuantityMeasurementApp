package com.app.arithmeticservice.util;

import com.app.arithmeticservice.exception.ArithmeticServiceException;
import java.util.Locale;

public final class QuantityMathHelper {
    private static final double EPSILON = 1e-6;
    private QuantityMathHelper() {}

    public static boolean compare(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "compare");
        return Math.abs(v1 - convert(v2, u2, u1, t1)) <= EPSILON;
    }

    /**
     * Add two quantities (both converted to the base unit of q1 before adding).
     * For temperature, addition is done in Celsius.
     */
    public static double add(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "add");
        String type = normalize(t1);
        if (type.equals("TemperatureUnit")) {
            // Convert both to Celsius, add, return in Celsius
            return toCelsius(v1, up(u1)) + toCelsius(v2, up(u2));
        }
        // Convert both to base unit, add, return raw base value
        return toBase(v1, u1, type) + toBase(v2, u2, type);
    }

    /**
     * Subtract q2 from q1 (both converted to base unit first).
     */
    public static double subtract(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "subtract");
        String type = normalize(t1);
        if (type.equals("TemperatureUnit")) {
            return toCelsius(v1, up(u1)) - toCelsius(v2, up(u2));
        }
        return toBase(v1, u1, type) - toBase(v2, u2, type);
    }

    /**
     * Multiply two quantities (both converted to base unit first).
     */
    public static double multiply(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "multiply");
        String type = normalize(t1);
        if (type.equals("TemperatureUnit")) {
            return toCelsius(v1, up(u1)) * toCelsius(v2, up(u2));
        }
        return toBase(v1, u1, type) * toBase(v2, u2, type);
    }

    /**
     * Divide q1 by q2 (both converted to base unit first).
     */
    public static double divide(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "divide");
        String type = normalize(t1);
        double divisor;
        if (type.equals("TemperatureUnit")) {
            divisor = toCelsius(v2, up(u2));
        } else {
            divisor = toBase(v2, u2, type);
        }
        if (Math.abs(divisor) < EPSILON) throw new ArithmeticServiceException("Division by zero");
        if (type.equals("TemperatureUnit")) {
            return toCelsius(v1, up(u1)) / divisor;
        }
        return toBase(v1, u1, type) / divisor;
    }

    /** Convert a raw base-unit value to the desired output unit. */
    public static double convertFromBase(double baseValue, String toUnit, String measurementType) {
        String type = normalize(measurementType), to = up(toUnit);
        if (type.equals("LengthUnit"))      return fromMeters(baseValue, to);
        if (type.equals("WeightUnit"))      return fromKg(baseValue, to);
        if (type.equals("VolumeUnit"))      return fromLiters(baseValue, to);
        if (type.equals("TemperatureUnit")) return fromCelsius(baseValue, to);
        throw new ArithmeticServiceException("Unsupported measurement type: " + measurementType);
    }

    /** Convert a value to the SI base unit for its type (meters, kg, liters). */
    private static double toBase(double v, String unit, String normalizedType) {
        String u = up(unit);
        if (normalizedType.equals("LengthUnit")) return toMeters(v, u);
        if (normalizedType.equals("WeightUnit")) return toKg(v, u);
        if (normalizedType.equals("VolumeUnit")) return toLiters(v, u);
        throw new ArithmeticServiceException("Cannot convert to base for type: " + normalizedType);
    }

    public static double convert(double value, String fromUnit, String toUnit, String measurementType) {
        String type = normalize(measurementType), from = up(fromUnit), to = up(toUnit);
        if (type.equals("LengthUnit"))      return fromMeters(toMeters(value, from), to);
        if (type.equals("WeightUnit"))      return fromKg(toKg(value, from), to);
        if (type.equals("VolumeUnit"))      return fromLiters(toLiters(value, from), to);
        if (type.equals("TemperatureUnit")) return fromCelsius(toCelsius(value, from), to);
        throw new ArithmeticServiceException("Unsupported measurement type: " + measurementType);
    }

    public static String normalize(String t) {
        if (t == null || t.trim().isEmpty()) throw new ArithmeticServiceException("Measurement type required");
        String n = t.trim().toUpperCase(Locale.ROOT);
        if (n.equals("LENGTH") || n.equals("LENGTHUNIT")) return "LengthUnit";
        if (n.equals("WEIGHT") || n.equals("WEIGHTUNIT")) return "WeightUnit";
        if (n.equals("VOLUME") || n.equals("VOLUMEUNIT")) return "VolumeUnit";
        if (n.equals("TEMPERATURE") || n.equals("TEMPERATUREUNIT")) return "TemperatureUnit";
        throw new ArithmeticServiceException("Invalid measurement type: " + t);
    }

    public static boolean isValidUnit(String unit, String type) {
        try { String u = up(unit), t = normalize(type);
            if (t.equals("LengthUnit")) return isLength(u);
            if (t.equals("WeightUnit")) return isWeight(u);
            if (t.equals("VolumeUnit")) return isVolume(u);
            if (t.equals("TemperatureUnit")) return isTemp(u);
        } catch (Exception ignored) {}
        return false;
    }

    private static void ensureSameType(String t1, String t2, String op) {
        if (!normalize(t1).equals(normalize(t2))) throw new ArithmeticServiceException(op + ": cannot mix " + t1 + " and " + t2);
    }

    private static String up(String s) {
        if (s == null || s.trim().isEmpty()) throw new ArithmeticServiceException("Unit required");
        return s.trim().toUpperCase(Locale.ROOT);
    }

    private static double toMeters(double v, String u) {
        if (u.equals("INCH")||u.equals("INCHES")) return v*0.0254;
        if (u.equals("FOOT")||u.equals("FEET")) return v*0.3048;
        if (u.equals("YARD")||u.equals("YARDS")) return v*0.9144;
        if (u.equals("CENTIMETER")||u.equals("CENTIMETERS")||u.equals("CM")) return v*0.01;
        if (u.equals("METER")||u.equals("METERS")||u.equals("M")) return v;
        throw new ArithmeticServiceException("Invalid length unit: "+u);
    }
    private static double fromMeters(double v, String u) {
        if (u.equals("INCH")||u.equals("INCHES")) return v/0.0254;
        if (u.equals("FOOT")||u.equals("FEET")) return v/0.3048;
        if (u.equals("YARD")||u.equals("YARDS")) return v/0.9144;
        if (u.equals("CENTIMETER")||u.equals("CENTIMETERS")||u.equals("CM")) return v/0.01;
        if (u.equals("METER")||u.equals("METERS")||u.equals("M")) return v;
        throw new ArithmeticServiceException("Invalid length unit: "+u);
    }
    private static double toKg(double v, String u) {
        if (u.equals("GRAM")||u.equals("GRAMS")||u.equals("G")) return v*0.001;
        if (u.equals("KILOGRAM")||u.equals("KILOGRAMS")||u.equals("KG")) return v;
        if (u.equals("POUND")||u.equals("POUNDS")||u.equals("LB")||u.equals("LBS")) return v*0.45359237;
        throw new ArithmeticServiceException("Invalid weight unit: "+u);
    }
    private static double fromKg(double v, String u) {
        if (u.equals("GRAM")||u.equals("GRAMS")||u.equals("G")) return v/0.001;
        if (u.equals("KILOGRAM")||u.equals("KILOGRAMS")||u.equals("KG")) return v;
        if (u.equals("POUND")||u.equals("POUNDS")||u.equals("LB")||u.equals("LBS")) return v/0.45359237;
        throw new ArithmeticServiceException("Invalid weight unit: "+u);
    }
    private static double toLiters(double v, String u) {
        if (u.equals("MILLILITER")||u.equals("MILLILITERS")||u.equals("ML")) return v*0.001;
        if (u.equals("LITER")||u.equals("LITERS")||u.equals("LITRE")||u.equals("LITRES")||u.equals("L")) return v;
        if (u.equals("GALLON")||u.equals("GALLONS")) return v*3.78541;
        throw new ArithmeticServiceException("Invalid volume unit: "+u);
    }
    private static double fromLiters(double v, String u) {
        if (u.equals("MILLILITER")||u.equals("MILLILITERS")||u.equals("ML")) return v/0.001;
        if (u.equals("LITER")||u.equals("LITERS")||u.equals("LITRE")||u.equals("LITRES")||u.equals("L")) return v;
        if (u.equals("GALLON")||u.equals("GALLONS")) return v/3.78541;
        throw new ArithmeticServiceException("Invalid volume unit: "+u);
    }
    private static double toCelsius(double v, String u) {
        if (u.equals("CELSIUS")||u.equals("C")) return v;
        if (u.equals("FAHRENHEIT")||u.equals("F")) return (v-32)*5.0/9.0;
        if (u.equals("KELVIN")||u.equals("K")) return v-273.15;
        throw new ArithmeticServiceException("Invalid temperature unit: "+u);
    }
    private static double fromCelsius(double v, String u) {
        if (u.equals("CELSIUS")||u.equals("C")) return v;
        if (u.equals("FAHRENHEIT")||u.equals("F")) return v*9.0/5.0+32;
        if (u.equals("KELVIN")||u.equals("K")) return v+273.15;
        throw new ArithmeticServiceException("Invalid temperature unit: "+u);
    }
    private static boolean isLength(String u) { return u.matches("INCH|INCHES|FOOT|FEET|YARD|YARDS|CENTIMETER|CENTIMETERS|CM|METER|METERS|M"); }
    private static boolean isWeight(String u) { return u.matches("GRAM|GRAMS|G|KILOGRAM|KILOGRAMS|KG|POUND|POUNDS|LB|LBS"); }
    private static boolean isVolume(String u) { return u.matches("MILLILITER|MILLILITERS|ML|LITER|LITERS|LITRE|LITRES|L|GALLON|GALLONS"); }
    private static boolean isTemp(String u)   { return u.matches("CELSIUS|C|FAHRENHEIT|F|KELVIN|K"); }
}
