package com.equality;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
public class QuantityMeasurementAppTest {
    @Test
    @DisplayName("Feet: Same value should be equal")
    void testFeetEquality_SameValue() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(1.0);
        assertEquals(f1, f2);
    }
    @Test
    @DisplayName("Feet: Different values should not be equal")
    void testFeetEquality_DifferentValue() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(2.0);
        assertNotEquals(f1, f2);
    }
    @Test
    @DisplayName("Feet: Null comparison should not be equal")
    void testFeetEquality_NullComparison() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertNotEquals(f1, null);
    }
    @Test
    @DisplayName("Feet: Type safety check")
    void testFeetEquality_TypeCheck() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertNotEquals(f1, "1.0");
    }
    @Test
    @DisplayName("Feet: Same reference should be equal")
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
        assertEquals(f1, f1);
    }
    @Test
    @DisplayName("Inches: Same value should be equal")
    void testInchesEquality_SameValue() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
        QuantityMeasurementApp.Inches i2 = new QuantityMeasurementApp.Inches(1.0);
        assertEquals(i1, i2);
    }
    @Test
    @DisplayName("Inches: Different values should not be equal")
    void testInchesEquality_DifferentValue() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
        QuantityMeasurementApp.Inches i2 = new QuantityMeasurementApp.Inches(2.0);
        assertNotEquals(i1, i2);
    }
    @Test
    @DisplayName("Inches: Null comparison should not be equal")
    void testInchesEquality_NullComparison() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
        assertNotEquals(i1, null);
    }
    @Test
    @DisplayName("Inches: Type safety check")
    void testInchesEquality_TypeCheck() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
        assertNotEquals(i1, "1.0");
    }
    @Test
    @DisplayName("Inches: Same reference should be equal")
    void testInchesEquality_SameReference() {
        QuantityMeasurementApp.Inches i1 = new QuantityMeasurementApp.Inches(1.0);
        assertEquals(i1, i1);
    }
    @Test
    @DisplayName("Static method: compareFeet should return true for equal values")
    void testCompareFeet() {
        assertTrue(QuantityMeasurementApp.compareFeet(2.0, 2.0));
        assertFalse(QuantityMeasurementApp.compareFeet(2.0, 3.0));
    }
    @Test
    @DisplayName("Static method: compareInches should return true for equal values")
    void testCompareInches() {
        assertTrue(QuantityMeasurementApp.compareInches(5.0, 5.0));
        assertFalse(QuantityMeasurementApp.compareInches(5.0, 6.0));
    }
}