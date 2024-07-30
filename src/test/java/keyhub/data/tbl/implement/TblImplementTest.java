package keyhub.data.tbl.implement;

import keyhub.data.tbl.Tbl;
import keyhub.data.tbl.schema.TblColumnSchema;
import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TblImplementTest {

    @Nested
    class ConstructorTest{
        @Test
        @DisplayName("rowMapList 를 이용한 Tbl 객체 생성")
        public void testOfMethodWithRowMapList() {
            List<Map<String, Object>> rowMapList = new ArrayList<>();
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("key1", "value1");
            rowMap.put("key2", 2);
            rowMapList.add(rowMap);

            Tbl result = TblImplement.of(rowMapList);

            System.out.println(result);
            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }

        @Test
        @DisplayName("columnListMap 을 이용한 Tbl 객체 생성")
        public void testOfMethodWithColumnListMap() {
            Map<String, List<Object>> columnListMap = new HashMap<>();
            List<Object> columnList1 = new ArrayList<>();
            columnList1.add("value1");
            columnList1.add("value2");
            columnList1.add("value3");
            columnListMap.put("key1", columnList1);

            List<Object> columnList2 = new ArrayList<>();
            columnList2.add(1);
            columnList2.add(2);
            columnList2.add(3);
            columnListMap.put("key2", columnList2);

            Tbl result = TblImplement.of(columnListMap);

            assertNotNull(result);
            assertEquals(2, result.getColumnSize());
            assertEquals(String.class, result.getColumnType(0));
            assertEquals(Integer.class, result.getColumnType(1));
        }
    }
}