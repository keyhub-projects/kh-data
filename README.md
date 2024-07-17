# KeyHub-Data
- This repository contains some classes about DataSet used in the KeyHub project.

# How to start
## Maven
```xml
<dependency>
    <groupId>io.github.keyhub-projects</groupId>
    <artifactId>kh-data</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Gradle
```gradle
implementation 'io.github.keyhub-projects:kh-data:1.0.1'
```

# Structure
## Class Diagram
- DataObject
  - DataValue
  - DataVariable
- Tbl
  - TblValue
    - TblValueBuilder
  - TblVariable
- JoinSet
  - InnerJoinSet
  - LeftJoinSet

![class_diagram](./docs/class_diagram.png)

# How to Use
- Almost setter made by fluent interface.
  - It returns the instance itself.
    - It means that you can chain methods.
    - For example, `tbl.select("a").where("b", ">", 10).selectAll()`.

## DataObject
- DataObject is a class that represents a object.

## DataValue
- DataValue is a class that represents a value.
- It extends DataObject.
- It is immutable class.
### Methods
- toVariable(): T extends DataVariable

## DataVariable
- DataVariable is a class that represents a variable.
- It extends DataObject.
- It is mutable class.
### Methods
- toValue(): T extends DataValue

## Tbl
- TBL means a "Table By List". 
- Tbl is a class that represents a dataset.
- It looks like CSV made by List.

### Constructors
- `of(List<String> columns): Tbl`
- `of(List<String> columns, List<List<Object>> data): Tbl`
- `builder(): TblValue.TblValueBuilder`

### Methods
#### about Columns
- `getColumnSize(): int`
- `getColumn(int index): String`
- `getColumns(): List<String>`
- `findColumnIndex(String column): Optional<Integer>`
#### about Rows
- `size(): int`
- `getRow(int index): List<Object>`
- `getRows(): List<List<Object>>`
#### about Join
- `leftJoin(Tbl right): JoinSet`
- `innerJoin(Tbl right): JoinSet`
#### about Select
- `select(String... columns): this`
- `selectAll(): this`
#### about Where
- `where(String column, String operator, Object value): this`
- `clearWhere(): this`
#### about data structure
- `getComputed(): this`
- `toRowMapList(): List<Map<String, Object>>`
- `toColumnMapList(): Map<String, List<Object>>`

## TblValue
- TblValue is a class that represents a dataset.
- TblValue is default implementation of Tbl.
  - It implements Tbl and DataValue.
    - DataValue is immutable class.
### Constructors
- `TblValue(List<String> columns)`
- `TblValue(List<String> columns, List<List<Object>> data)`
- `TblValue(TblValueBuilder builder)`
### Methods
- `toVariable(): TblVariable`
### TblValueBuilder
- TblValueBuilder is a builder class for TblValue.
- It is used to create TblValue in Tbl interface.
#### Methods
- `addColumns(List<String> columns): this`
- `addRows(List<List<Object>> data): this`
- `build(): Tbl`

## TblVariable
- TblVariable is a class that represents a dataset.
- TblVariable is optional implementation of Tbl.
  - It implements Tbl and DataVariable.
    - DataVariable is mutable class.
### Constructors
- `TblVariable()`
- `TblVariable(List<String> columns)`
- `TblVariable(List<String> columns, List<List<Object>> data)`
### Methods
- `addColumn(String column): this`
- `addColumns(List<String> columns): this`
- `addRow(List<Object> row): this`
- `addRows(List<List<Object>> data): this`
- `toValue(): TblValue`

## JoinSet
- JoinSet is a class that represents a join operation between two datasets.
- It looks like a join operation in SQL.
### methods
- `toTbl(): Tbl`
- `on(String sameKey): this`
- `on(String leftKey, String rightKey): this`
- `selectAll(): this`
- `selectFromLeft(String column): this`
- `selectFromLeft(String... columns): this`
- `selectAllFromLeft(): this`
- `selectFromRight(String column): this`
- `selectFromRight(String... columns): this`
- `selectAllFromRight(): this`
- `findColumnIndexFromLeft(String column): Optional<Integer>`
- `findColumnIndexFromRight(String column): Optional<Integer>`


