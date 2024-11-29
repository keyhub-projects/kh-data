# KeyHub-Data
- This repository contains some classes about DataSet used in the KeyHub project.

# How to start
## Maven
```xml
<dependency>
    <groupId>io.github.keyhub-projects</groupId>
    <artifactId>kh-data-structure</artifactId>
    <version>1.0.1</version>
</dependency>
```

## Gradle
```gradle
implementation 'io.github.keyhub-projects:kh-data-structure:1.0.1'
```

# Structure

## KhTable
### Class Diagram

![class_diagram](./docs/class_diagram.png)

## How to Use
- Almost setter made by fluent interface.
  - It returns the instance itself.
    - It means that you can chain methods.
- For example,
  - You can use the `KhTable.from()` method to convert the `List<Map<String, Object>>` to `Tbl`.
  - You can use the `toRowMapList()` method to convert the `KhTable` to `List<Map<String, Object>>`.
  - [This is an example of how to use the `KhTable` class.](./kh-data-example/src/main/java/keyhub/order/infrastructure/OrderReaderImplement.java)
    - [test code](./kh-data-example/src/test/java/keyhub/order/domain/OrderReaderTest.java)
```java
@Override
public List<OrderDetailView> findOrderedDetailViewList(String userId){
  List<Order> orderList = orderJpaRepository.findByUserId(userId);
  List<PgView> pgViewList = pgClient.findPgViewListByUserId(userId);
  List<WmsView> wmsViewList = wmsClient.findWmsViewListByUserId(userId);

  KhTable orderTbl = KhTable.from(orderList);
  KhTable pgTbl = KhTable.from(pgViewList);
  KhTable wmsTbl = KhTable.from(wmsViewList);
  KhTable result = orderTbl
          .innerJoin(pgTbl)
            .on("id", "orderId")
            .selectAll()
          .toOne()
          .innerJoin(wmsTbl)
            .on("id", "orderId")
            .selectAll()
          .toOne();
  List<Map<String, Object>> mapList = result.toRowMapList();
  return mapList.stream()
          .map(OrderDetailView::fromRowMap)
          .toList();
}
```


