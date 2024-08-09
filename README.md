# KeyHub-Data
- This repository contains some classes about DataSet used in the KeyHub project.

# How to start
## Maven
```xml
<dependency>
    <groupId>io.github.keyhub-projects</groupId>
    <artifactId>kh-data</artifactId>
    <version>1.3.0</version>
</dependency>
```

## Gradle
```gradle
implementation 'io.github.keyhub-projects:kh-data:1.3.0'
```

# Structure

## Tbl
### Class Diagram

![class_diagram](./docs/class_diagram.png)

## How to Use
- Almost setter made by fluent interface.
  - It returns the instance itself.
    - It means that you can chain methods.
    - For example, `tbl.where("b", EQUAL, 10).selectAll()`.
- You can use the `Tbl.from()` method to convert the `List<Map<String, Object>>` to `Tbl`.
- You can use the `toRowMapList()` method to convert the `Tbl` to `List<Map<String, Object>>`.
- [This is an example of how to use the `Tbl` class.](./kh-data-tbl-example/src/main/java/keyhub/order/infrastructure/OrderReaderImplement.java)
  - [test code](./kh-data-tbl-example/src/test/java/keyhub/order/domain/OrderReaderTest.java)
```java
public List<OrderDetailView> findOrderedDetailViewList(String userId) throws IllegalAccessException {
        List<Order> orderList = orderJpaRepository.findByUserId(userId);
        List<PgView> pgViewList = pgClient.findPgViewListByUserId(userId);
        List<WmsView> wmsViewList = wmsClient.findWmsViewListByUserId(userId);

        Tbl orderTbl = Tbl.from(orderList);
        Tbl pgTbl = Tbl.from(pgViewList);
        Tbl wmsTbl = Tbl.from(wmsViewList);
        Tbl result = orderTbl.innerJoin(pgTbl)
                .on("id", "orderId")
                .selectAll()
                .toTbl()
                .innerJoin(wmsTbl)
                .on("id", "orderId")
                .selectAll()
                .toTbl();
        List<Map<String, Object>> mapList = result.toRowMapList();
        return mapList.stream()
                .map(OrderDetailView::fromRowMap)
                .toList();
    }
```

- [KeyHub-Data-Tbl(한글 설명)_1.2.0](./docs/KeyHub-Data-Tbl(한글설명)_1.2.0.pdf)

