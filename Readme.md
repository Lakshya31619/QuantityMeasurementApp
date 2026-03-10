#  Quantity Measurement Application

##  Project Overview

This project demonstrates the **incremental evolution** of a Quantity Measurement system through structured use cases:

-  UC1 – Equality comparison for Feet  
-  UC2 – Equality comparison for Feet and Inches  
-  UC3 – Generic scalable design with cross-unit equality  
-  UC4 – Added Yard and Centimeter support  
-  UC5 – Robust conversion API  
-  UC6 – Addition of quantities  
-  UC7 – Explicit target unit addition 
-  UC8 – Standalone unit enum with conversion responsibility
-  UC9 – Weight measurement support
-  UC10 – Generic Quantity class using interface
-  UC11 – Volume measurement support
-  UC12 – Subtraction and division operations
-  UC13 – Centralized arithmetic logic (DRY enforcement)
-  UC14 – Temperature measurement with special conversion logic 

Each use case improves **design quality, scalability, and maintainability**.

---

#  Project Structure
```
QuantityMeasurementApp/
│
├── src/main/java/com/equality/
|   ├── ArithmeticOperation.java
│   ├── LengthUnit.java
|   ├── Measurable.java
│   ├── QuantityLength.java
│   ├── QuantityMeasurementApp.java
│   ├── TemperatureUnit.java
│   ├── VolumeUnit.java
|   └── WeightUnit.java
│
├── src/test/java/com/equality/
│   └── QuantityMeasurementAppTest.java
│
└── pom.xml
```

--- 

#  UC1 – Feet Equality

##  Objective
Support equality comparison for **Feet** only.

##  Features
 Value-based equality  
 Floating-point safe comparison  
 Null & type safety  

## ⚠ Limitation
 Only Feet supported  
 Not scalable  

---

#  UC2 – Feet & Inches Equality

##  Objective
Add support for **Inches**.

##  Features
 Same-unit equality  
 Improved test coverage  

##  Design Issue
 Duplicate logic (violates DRY)  
 No cross-unit comparison  

---

#  UC3 – Generic QuantityLength

##  Objective
Refactor to remove duplication and enable scalability.

##  Implementation
- `LengthUnit` enum (conversion factor to base unit)
- `QuantityLength` class

##  Features
 Cross-unit equality (1 ft = 12 in)  
 DRY compliant  
 Enum-based type safety  
 Scalable architecture  

---

#  UC4 – Extended Unit Support

##  Objective
Add more units without changing business logic.

##  Added Units
- YARD  
- CENTIMETER  

##  Features
 1 yd = 3 ft  
 1 in = 2.54 cm  
 No change in equality logic  
 Open/Closed Principle followed  

---

#  UC5 – Conversion API

##  Objective
Provide a robust unit conversion feature.

##  Features
 `convert(value, source, target)`  
 Floating-point precision handling (EPSILON)  
 Null & invalid input validation  
 Round-trip conversion safe  

---

#  UC6 – Quantity Addition

##  Objective
Support arithmetic operations.

##  Features
 Same-unit addition  
 Cross-unit addition  
 Negative value handling  
 Commutative property  

Example:

1 ft + 12 in = 2 ft  

---

#  UC7 – Explicit Target Unit Addition

##  Objective
Allow addition result in **any specified unit**.

##  Example

```java
feet.add(inches, LengthUnit.YARD);
```

##  Features
 Result in any unit  
 No logic duplication  
 Fully scalable  
 Maintains precision  

Example:

1 ft + 12 in → 24 in  
1 ft + 12 in → 0.67 yd  

---

#  UC8 – Standalone Unit Enum Refactoring

##  Objective
Refactor unit enums into standalone classes and assign them conversion responsibility.

##  Features
 Standalone LengthUnit enum  
 Unit handles conversion logic  
 Reduced coupling between unit and quantity classes  
 Improved architecture scalability  

Example:

1 ft = 12 in  
1 yd = 3 ft  

---

#  UC9 – Weight Measurement Support

##  Objective
Introduce support for weight measurement units.

##  Units
 Kilogram (base unit)  
 Gram  
 Pound  

##  Features
 Cross-unit equality comparison  
 Weight unit conversion  
 Arithmetic addition support  
 Separation from length measurement  

Example:

1 kg = 1000 g  
1 lb ≈ 0.453592 kg  

---

#  UC10 – Generic Quantity Class

##  Objective
Refactor the system to use a generic quantity class supporting multiple measurement categories.

##  Implementation
 Quantity<U extends Measurable>  
 Measurable interface  

##  Features
 Single reusable quantity class  
 Eliminates duplicate quantity classes  
 Compile-time type safety  
 Prevents cross-category comparison  

Example:

Quantity<LengthUnit>  
Quantity<WeightUnit>  

---

#  UC11 – Volume Measurement Support

##  Objective
Extend the system to support volume measurement.

##  Units
 Litre (base unit)  
 Millilitre  
 Gallon  

##  Features
 Cross-unit equality  
 Conversion support  
 Addition support  
 Reuses generic quantity class  

Example:

1 L = 1000 mL  
1 gallon ≈ 3.78541 L  

---

#  UC12 – Subtraction and Division Operations

##  Objective
Extend arithmetic capabilities beyond addition.

##  Features
 Cross-unit subtraction  
 Division ratio calculation  
 Explicit target unit result support  
 Input validation and error handling  

Examples:

5 ft − 24 in = 3 ft  
3 kg − 500 g = 2.5 kg  

Division example:

10 kg ÷ 5 kg = 2  

---

#  UC13 – Centralized Arithmetic Logic

##  Objective
Refactor arithmetic operations to remove duplicated logic.

##  Features
 Centralized arithmetic helper method  
 ArithmeticOperation enum  
 DRY principle enforcement  
 Consistent validation and conversion logic  

Supported Operations:

 Addition  
 Subtraction  
 Division  

Example:

performOperation(this, other, ArithmeticOperation.ADD)

---

#  UC14 – Temperature Measurement Support

##  Objective
Support temperature measurements with special conversion formulas.

##  Units
 Celsius  
 Fahrenheit  
 Kelvin  

##  Features
 Temperature equality comparison  
 Temperature conversion support  
 Special conversion formulas  
 Arithmetic operations restricted  

Examples:

0°C = 32°F  
0°C = 273.15K  

Conversion formulas:

F = (C × 9/5) + 32  
C = (F − 32) × 5/9  
K = C + 273.15  

---

#  Evolution Summary

| Feature | UC1 | UC2 | UC3 | UC4 | UC5 | UC6 | UC7 | UC8 | UC9 | UC10 | UC11 | UC12 | UC13 | UC14 |
|----------|------|------|------|------|------|------|------|------|------|------|------|------|------|------|
| Feet | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Inches | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Yard | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Centimeter | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Kilogram | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Gram | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Pound | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Litre | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Millilitre | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Gallon | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Celsius | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| Fahrenheit | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| Kelvin | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| Cross-unit equality | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Conversion API | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Addition | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |
| Subtraction | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ❌ |
| Division | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ❌ |
| Target unit addition | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |

---

#  Concepts Covered

- Object Equality Contract  
- DRY Principle  
- Refactoring  
- Enum Usage  
- Generics and Type Safety  
- Defensive Programming  
- Floating-point Handling  
- Clean Architecture  
- Scalable System Design  

---

#  Final Outcome

The system evolved from a simple equality check (UC1) into a **fully extensible quantity measurement framework (UC14)** supporting:

✔ Equality comparison  
✔ Unit conversion  
✔ Arithmetic operations (addition, subtraction, division)  
✔ Multiple measurement categories (length, weight, volume, temperature)  
✔ Generic architecture using interfaces and generics  
✔ Centralized arithmetic logic following DRY principle  
✔ Production-ready scalable design  

---