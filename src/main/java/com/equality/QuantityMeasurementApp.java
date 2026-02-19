package com.equality;
import java.util.Objects;
public class QuantityMeasurementApp {
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);
        private final double toFeetFactor;
        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }
        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;
        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }
        public double getValue() {
            return value;
        }
        public LengthUnit getUnit() {
            return unit;
        }
        private double toBaseUnit() {
            return unit.toFeet(value);
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
        @Override
        public int hashCode() {
            return Objects.hash(toBaseUnit());
        }
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }
    public static boolean compare(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {
        QuantityLength q1 = new QuantityLength(value1, unit1);
        QuantityLength q2 = new QuantityLength(value2, unit2);
        return q1.equals(q2);
    }
    public static void main(String[] args) {
        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" + compare(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH) + ")");
        System.out.println();
        System.out.println("Input: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: Equal (" + compare(1.0, LengthUnit.INCH, 1.0, LengthUnit.INCH) + ")");
    }
}