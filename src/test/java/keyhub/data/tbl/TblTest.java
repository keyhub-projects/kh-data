package keyhub.data.tbl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TblTest {

    @Test
    public void testOfWithColumnsAndData() {
        List<String> columns = Arrays.asList("column1", "column2", "column3");
        List<List<Object>> data = Arrays.asList(
                Arrays.asList("item1", "item2", "item3"),
                Arrays.asList("item4", "item5", "item6"),
                Arrays.asList("item7", "item8", "item9")
        );
        Tbl tbl = Tbl.of(columns, data);
        assertEquals(columns, tbl.getColumns());
        assertEquals(data, tbl.getRows());
    }

    @Test
    public void testOfWithRowMapList() {
        Map<String, Object> row1 = new HashMap<>();
        row1.put("column1", "item1-1");
        row1.put("column2", "item1-2");
        row1.put("column3", "item1-3");
        Map<String, Object> row2 = new HashMap<>();
        row2.put("column1", "item2-1");
        row2.put("column2", "item2-2");
        row2.put("column3", "item2-3");
        List<Map<String, Object>> rowMapList = Arrays.asList(row1, row2);
        Tbl tbl = Tbl.of(rowMapList);

        System.out.println(rowMapList);
        System.out.println(tbl.getColumns());
        System.out.println(tbl.getRows());
        System.out.println(tbl);
        assertEquals(rowMapList.getFirst().keySet().size(), tbl.getColumns().size());
        assertEquals(rowMapList.getFirst().keySet().toString(), tbl.getColumns().toString());
    }

    @Test
    public void testOfWithColumnListMap() {
        Map<String, List<Object>> columnMapList = new HashMap<>();
        columnMapList.put("column1", Arrays.asList("item1-1", "item2-1"));
        columnMapList.put("column2", Arrays.asList("item1-2", "item2-2"));
        columnMapList.put("column3", Arrays.asList("item1-3", "item2-3"));
        Tbl tbl = Tbl.of(columnMapList);

        System.out.println(columnMapList);
        System.out.println(tbl);
        assertEquals(columnMapList.keySet().toString(), tbl.getColumns().toString());
        assertEquals(
                columnMapList.get("column1").toString(),
                tbl.select("column1").getRows().stream().map(List::getFirst).toList().toString()
        );
        assertEquals(
                columnMapList.get("column2").toString(),
                tbl.select("column2").getRows().stream().map(List::getFirst).toList().toString()
        );
    }

    @Nested
    class OfTestWithDto {
        class Dto {
            String column1;
            String column2;
            String column3;

            Dto(String column1, String column2, String column3) {
                this.column1 = column1;
                this.column2 = column2;
                this.column3 = column3;
            }
        }

        @Test
        public void testOfWithDto() {
            Dto dto1 = new Dto("item1-1", "item1-2", "item1-3");
            Dto dto2 = new Dto("item2-1", "item2-2", "item2-3");
            List<Dto> dtoList = Arrays.asList(dto1, dto2);
            Tbl tbl = Tbl.of(dtoList, Dto.class);

            System.out.println(tbl);
            assertEquals(
                    Arrays.asList("column1", "column2", "column3"),
                    tbl.getColumns()
            );
            assertEquals(
                    List.of(dtoList.get(0).column1, dtoList.get(1).column1),
                    tbl.getRows().stream().map(row -> row.get(0)).toList()
            );
            assertEquals(
                    List.of(dtoList.get(0).column2, dtoList.get(1).column2),
                    tbl.getRows().stream().map(row -> row.get(1)).toList()
            );

        }
    }

}