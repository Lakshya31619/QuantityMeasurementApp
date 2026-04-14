package com.app.conversionservice.util;

import com.app.conversionservice.exception.ConversionException;
import java.util.Locale;

public final class QuantityMathHelper {
    private static final double EPSILON = 1e-6;
    private QuantityMathHelper() {}

    public static boolean compare(double v1, String u1, String t1, double v2, String u2, String t2) {
        ensureSameType(t1, t2, "compare");
        return Math.abs(v1 - convert(v2, u2, u1, t1)) <= EPSILON;
    }

    public static double convert(double value, String fromUnit, String toUnit, String measurementType) {
        String type = normalize(measurementType), from = up(fromUnit), to = up(toUnit);
        if (type.equals("LengthUnit"))      return fromMeters(toMeters(value, from), to);
        if (type.equals("WeightUnit"))      return fromKg(toKg(value, from), to);
        if (type.equals("VolumeUnit"))      return fromLiters(toLiters(value, from), to);
        if (type.equals("TemperatureUnit")) return fromCelsius(toCelsius(value, from), to);
        throw new ConversionException("Unsupported measurement type: " + measurementType);
    }

    public static String normalize(String t) {
        if (t == null || t.trim().isEmpty()) throw new ConversionException("Measurement type required");
        String n = t.trim().toUpperCase(Locale.ROOT);
        if (n.equals("LENGTH") || n.equals("LENGTHUNIT")) return "LengthUnit";
        if (n.equals("WEIGHT") || n.equals("WEIGHTUNIT")) return "WeightUnit";
        if (n.equals("VOLUME") || n.equals("VOLUMEUNIT")) return "VolumeUnit";
        if (n.equals("TEMPERATURE") || n.equals("TEMPERATUREUNIT")) return "TemperatureUnit";
        throw new ConversionException("Invalid measurement type: " + t);
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
        if (!normalize(t1).equals(normalize(t2))) throw new ConversionException(op + ": cannot mix " + t1 + " and " + t2);
    }

    private static String up(String s) {
        if (s == null || s.trim().isEmpty()) throw new ConversionException("Unit required");
        return s.trim().toUpperCase(Locale.ROOT);
    }

    private static double toMeters(double v, String u) {
        if (u.equals("INCH")||u.equals("INCHES")) return v*0.0254;
        if (u.equals("FOOT")||u.equals("FEET")) return v*0.3048;
        if (u.equals("YARD")||u.equals("YARDS")) return v*0.9144;
        if (u.equals("CENTIMETER")||u.equals("CENTIMETERS")||u.equals("CM")) return v*0.01;
        if (u.equals("METER")||u.equals("METERS")||u.equals("M")) return v;
        throw new ConversionException("Invalid length unit: "+u);
    }
    private static double fromMeters(double v, String u) {
        if (u.equals("INCH")||u.equals("INCHES")) return v/0.0254;
        if (u.equals("FOOT")||u.equals("FEET")) return v/0.3048;
        if (u.equals("YARD")||u.equals("YARDS")) return v/0.9144;
        if (u.equals("CENTIMETER")||u.equals("CENTIMETERS")||u.equals("CM")) return v/0.01;
        if (u.equals("METER")||u.equals("METERS")||u.equals("M")) return v;
        throw new ConversionException("Invalid length unit: "+u);
    }
    private static double toKg(double v, String u) {
        if (u.equals("GRAM")||u.equals("GRAMS")||u.equals("G")) return v*0.001;
        if (u.equals("KILOGRAM")||u.equals("KILOGRAMS")||u.equals("KG")) return v;
        if (u.equals("POUND")||u.equals("POUNDS")||u.equals("LB")||u.equals("LBS")) return v*0.45359237;
        throw new ConversionException("Invalid weight unit: "+u);
    }
    private static double fromKg(double v, String u) {
        if (u.equals("GRAM")||u.equals("GRAMS")||u.equals("G")) return v/0.001;
        if (u.equals("KILOGRAM")||u.equals("KILOGRAMS")||u.equals("KG")) return v;
        if (u.equals("POUND")||u.equals("POUNDS")||u.equals("LB")||u.equals("LBS")) return v/0.45359237;
        throw new ConversionException("Invalid weight unit: "+u);
    }
    private static double toLiters(double v, String u) {
        if (u.equals("MILLILITER")||u.equals("MILLILITERS")||u.equals("ML")) return v*0.001;
        if (u.equals("LITER")||u.equals("LITERS")||u.equals("LITRE")||u.equals("LITRES")||u.equals("L")) return v;
        if (u.equals("GALLON")||u.equals("GALLONS")) return v*3.78541;
        throw new ConversionException("Invalid volume unit: "+u);
    }
    private static double fromLiters(double v, String u) {
        if (u.equals("MILLILITER")||u.equals("MILLILITERS")||u.equals("ML")) return v/0.001;
        if (u.equals("LITER")||u.equals("LITERS")||u.equals("LITRE")||u.equals("LITRES")||u.equals("L")) return v;
        if (u.equals("GALLON")||u.equals("GALLONS")) return v/3.78541;
        throw new ConversionException("Invalid volume unit: "+u);
    }
    private static double toCelsius(double v, String u) {
        if (u.equals("CELSIUS")||u.equals("C")) return v;
        if (u.equals("FAHRENHEIT")||u.equals("F")) return (v-32)*5.0/9.0;
        if (u.equals("KELVIN")||u.equals("K")) return v-273.15;
        throw new ConversionException("Invalid temperature unit: "+u);
    }
    private static double fromCelsius(double v, String u) {
        if (u.equals("CELSIUS")||u.equals("C")) return v;
        if (u.equals("FAHRENHEIT")||u.equals("F")) return v*9.0/5.0+32;
        if (u.equals("KELVIN")||u.equals("K")) return v+273.15;
        throw new ConversionException("Invalid temperature unit: "+u);
    }
    private static boolean isLength(String u) { return u.matches("INCH|INCHES|FOOT|FEET|YARD|YARDS|CENTIMETER|CENTIMETERS|CM|METER|METERS|M"); }
    private static boolean isWeight(String u) { return u.matches("GRAM|GRAMS|G|KILOGRAM|KILOGRAMS|KG|POUND|POUNDS|LB|LBS"); }
    private static boolean isVolume(String u) { return u.matches("MILLILITER|MILLILITERS|ML|LITER|LITERS|LITRE|LITRES|L|GALLON|GALLONS"); }
    private static boolean isTemp(String u)   { return u.matches("CELSIUS|C|FAHRENHEIT|F|KELVIN|K"); }
}
