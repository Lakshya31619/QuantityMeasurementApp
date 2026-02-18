package com.equality;
public class QuantityMeasurementApp {
    public static class Feet {
        private final double value;
        public Feet(double value) {
            this.value = value;
        }
        public double getValue() {
            return value;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }
    public static class Inches {
        private final double value;
        public Inches(double value) {
            this.value = value;
        }
        public double getValue() {
            return value;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }
    public static boolean compareFeet(double value1, double value2) {
        Feet feet1 = new Feet(value1);
        Feet feet2 = new Feet(value2);
        return feet1.equals(feet2);
    }
    public static boolean compareInches(double value1, double value2) {
        Inches inch1 = new Inches(value1);
        Inches inch2 = new Inches(value2);
        return inch1.equals(inch2);
    }
    public static void main(String[] args) {
        double feetValue1 = 1.0;
        double feetValue2 = 1.0;
        double inchValue1 = 1.0;
        double inchValue2 = 1.0;
        System.out.println("Input: " + feetValue1 + " ft and " + feetValue2 + " ft");
        System.out.println("Output: Equal (" + compareFeet(feetValue1, feetValue2) + ")");
        System.out.println();
        System.out.println("Input: " + inchValue1 + " inch and " + inchValue2 + " inch");
        System.out.println("Output: Equal (" + compareInches(inchValue1, inchValue2) + ")");
    }
}