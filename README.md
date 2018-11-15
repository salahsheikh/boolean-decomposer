# Boolean Decomposer
> Parses and computes a truth table, minterms, and maxterms for any logical expression

# Usage example

Example code:
```java
String exp = "A + (B XOR C) NAND B";
TruthTable t = new TruthTable(exp);
t.generateTruthTable();
t.print();

System.out.println("MINTERMS:");
System.out.println(t.getMinterms().stream().map(String::toString).collect(Collectors.joining(" + ")));
System.out.println("MAXTERMS:");
System.out.println(t.getMaxterms().stream().map(String::toString).collect(Collectors.joining()));
```

For the logical expression `"A + (B XOR C) NAND B"`

```
A	B	C	OUTPUT
0	0	0	1
0	0	1	1
0	1	0	0
0	1	1	1
1	0	0	1
1	0	1	1
1	1	0	0
1	1	1	0
MINTERMS:
A'B'C' + A'B'C + A'BC + AB'C' + AB'C
MAXTERMS:
(A'+B+C')(A+B+C')(A+B+C)
```

# Supported Logical Functions

* AND
* OR
* NOT
* NAND
* NOR
* XOR
* XNOR

Also supports alternate representations for logical functions such as "&&" (AND) and "+" (OR).

# Building

Simply run `gradle build` for Linux/Unix or `./gradlew build` for Windows