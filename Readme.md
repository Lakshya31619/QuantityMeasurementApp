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

Each use case improves **design quality, scalability, and maintainability**.

---

#  Project Structure
```
QuantityMeasurementApp/
│
├── src/main/java/com/equality/
│   ├── LengthUnit.java
│   ├── QuantityLength.java
│   └── QuantityMeasurementApp.java
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

#  Evolution Summary

| Feature | UC1 | UC2 | UC3 | UC4 | UC5 | UC6 | UC7 |
|----------|------|------|------|------|------|------|------|
| Feet | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Inches | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Yard | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Centimeter | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Cross-unit equality | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Conversion API | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ |
| Addition | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ |
| Target unit addition | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |

---

#  Concepts Covered

- Object Equality Contract  
- DRY Principle  
- Refactoring  
- Enum Usage  
- Defensive Programming  
- Floating-point Handling  
- Clean Architecture  
- Scalable Design  

---

#  Final Outcome

The system evolved from a simple equality check (UC1) into a **clean, extensible, mini quantity measurement framework (UC7)** supporting:

✔ Equality  
✔ Conversion  
✔ Arithmetic operations  
✔ Flexible result units  
✔ Production-ready design  

---