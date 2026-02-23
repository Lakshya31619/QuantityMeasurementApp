# 📌 Quantity Measurement Application

## 🚀 Project Overview

This project demonstrates the **incremental evolution** of a Quantity Measurement system through six structured use cases:

- 🟢 **UC1** – Equality comparison for *Feet*
- 🟡 **UC2** – Equality comparison for *Feet and Inches*
- 🔵 **UC3** – Generic, scalable `QuantityLength` with cross-unit comparison
- 🟣 **UC4** – Extended unit support (Yard & Centimeter)
- 🟤 **UC5** – Robust unit conversion API
- 🟠 **UC6** – Arithmetic operations (Addition)

Each use case improves **design quality, scalability, maintainability, and architectural clarity**.

---

# 🟢 UC1 – Feet Measurement Equality

## 🎯 Objective
Implement equality comparison for a single measurement unit: **Feet**.

## 🏗 Implementation
- Class: `Feet`
- Field: `double value`
- Overrides:
  - `equals(Object obj)`
  - `hashCode()`

### Equality Logic

Double.compare(this.value, other.value) == 0


## ✅ Features
✔ Reflexive equality  
✔ Symmetric equality  
✔ Transitive equality  
✔ Null safety  
✔ Type safety  
✔ Floating-point safe comparison  

## ⚠ Limitation
❌ Supports only Feet  
❌ Adding new units would cause duplication  

---

# 🟡 UC2 – Feet and Inches Equality

## 🎯 Objective
Extend UC1 to support **Inches** in addition to Feet.

## 🏗 Implementation
- Class: `Feet`
- Class: `Inches`

Both classes:
- Store a `double value`
- Override `equals()`
- Override `hashCode()`

🚫 Cross-unit comparison is NOT supported.

## ✅ Features
✔ Equality within same unit  
✔ Null safety  
✔ Type safety  
✔ Static comparison methods  
✔ Improved test coverage  

## ⚠ Design Issue

Violates **DRY (Don't Repeat Yourself) Principle**:

- Duplicate constructors  
- Duplicate `equals()` logic  
- Duplicate `hashCode()` logic  

Not scalable for future units.

---

# 🔵 UC3 – Generic QuantityLength (Refactored Design)

## 🎯 Objective
Refactor UC2 to:
- Remove duplication  
- Enable cross-unit equality  
- Improve scalability  

---

## 🏗 Implementation

### 1️⃣ Enum: `LengthUnit`

Defines conversion factors to base unit (Feet):


FEET(1.0)
INCH(1.0 / 12.0)


---

### 2️⃣ Class: `QuantityLength`

Encapsulates:
- `double value`
- `LengthUnit unit`

### 🔄 Equality Logic

Both values are converted to base unit before comparison:


Double.compare(this.toFeet(), other.toFeet()) == 0


---

## ✅ Features

✔ DRY Principle applied  
✔ Cross-unit comparison (1 ft = 12 in)  
✔ Enum-based type safety  
✔ Conversion abstraction  
✔ Scalable architecture  
✔ Full equality contract compliance  

---

## 🧪 Example Comparisons

| Comparison | Result |
|------------|--------|
| 1 ft vs 1 ft | ✅ true |
| 1 inch vs 1 inch | ✅ true |
| 1 ft vs 12 inch | ✅ true |
| 1 ft vs 2 ft | ❌ false |

---

# 🟣 UC4 – Extended Unit Support (Yard & Centimeter)

## 🎯 Objective
Enhance system to support additional units without modifying equality logic.

New units added:
- 🟫 Yard  
- 🟩 Centimeter  

---

## 🏗 Implementation

### Updated `LengthUnit` Enum


FEET(1.0)
INCH(1.0 / 12.0)
YARD(3.0)
CENTIMETER((0.393701) / 12.0)


Each unit defines its conversion factor to **Feet**, keeping conversion centralized.

---

## 🔄 Cross-Unit Equality Examples

| Comparison | Result |
|------------|--------|
| 1 yard vs 3 feet | ✅ true |
| 1 yard vs 36 inches | ✅ true |
| 1 cm vs 0.393701 inch | ✅ true |

---

## ✅ Improvements

✔ Open/Closed Principle followed  
✔ No changes required in equality logic  
✔ Fully backward compatible  
✔ Easily extendable architecture  

---

# 🟤 UC5 – Robust Unit Conversion API

## 🎯 Objective
Introduce a complete **unit conversion feature** with:

- Bidirectional conversion  
- Method overloading  
- Defensive programming  
- Floating-point precision handling  

---

## 🏗 Implementation Enhancements

### New Methods in `QuantityLength`


convertTo(LengthUnit targetUnit)
toFeet()


### Precision Handling


private static final double EPSILON = 1e-6;


Used for safe floating-point comparison.

---

## 🔄 Conversion Examples

| Conversion | Result |
|------------|--------|
| 1 ft → inch | 12 |
| 3 yard → feet | 9 |
| 36 inch → yard | 1 |
| 1 cm → inch | 0.393701 |
| 1 ft → cm | 30.48 |

---

## 🧪 Edge Case Handling

✔ Zero value conversion  
✔ Negative values  
✔ Same-unit conversion  
✔ Round-trip preservation  
✔ Null validation  
✔ Illegal argument protection  

---

# 🟠 UC6 – Length Addition (Arithmetic Operations)

## 🎯 Objective
Extend the system to support **addition across same and different units**.

---

## 🏗 Implementation

### New Methods in `QuantityLength`


add(QuantityLength other, LengthUnit resultUnit)
add(QuantityLength other)


---

## 🔄 Addition Logic

1. Convert both values to base unit (Feet)
2. Add them
3. Convert result to desired unit


double sumFeet = thisFeet + otherFeet;


---

## ➕ Addition Examples

| Operation | Result |
|------------|--------|
| 1 ft + 2 ft | 3 ft |
| 12 in + 1 ft | 24 in |
| 1 yard + 3 ft | 2 yard |
| 2.54 cm + 1 in | 5.08 cm |
| 5 ft + (-2 ft) | 3 ft |

---

## 🔁 Verified Mathematical Properties

✔ Commutativity  
✔ Zero identity  
✔ Cross-unit addition  
✔ Negative value handling  
✔ Precision consistency  

---

# 🔄 Complete Evolution Summary (UC1 → UC6)

| Feature | UC1 | UC2 | UC3 | UC4 | UC5 | UC6 |
|----------|------|------|------|------|------|------|
| Feet support | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Inches support | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Yard support | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ |
| Centimeter support | ❌ | ❌ | ❌ | ✅ | ✅ | ✅ |
| Cross-unit equality | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Conversion API | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ |
| Addition | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |
| DRY compliant | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |
| Scalable design | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ |

---

# 📚 Concepts Covered

- 📏 Object Equality Contract  
- 🛡 Encapsulation  
- 🔢 Floating-point comparison  
- ♻ DRY Principle  
- 🔄 Refactoring  
- 🧩 Enum usage  
- 🏗 Clean Architecture  
- 📈 Scalable Design  
- 🧠 Defensive Programming  
- ➕ Arithmetic modeling  
- 🔁 Method Overloading  
- 🧪 Edge case validation  

---

# 🏆 Final Outcome

By UC6, the application evolves into a:

## 📏 Clean, Extensible Quantity Measurement Framework

It demonstrates:

- Progressive refactoring  
- Open/Closed Principle  
- DRY compliance  
- Floating-point safe design  
- Cross-unit comparison  
- Conversion engine  
- Arithmetic operations  

---