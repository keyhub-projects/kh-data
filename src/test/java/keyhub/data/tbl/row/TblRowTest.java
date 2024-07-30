package keyhub.data.tbl.row;

import keyhub.data.tbl.schema.TblSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TblRowTest {

    @Nested
    class ConstructorTest{

        @Test
        @DisplayName("스키마와 값이 주어지면 TblRow 객체 생성")
        public void testOfWithSchemaAndVarargsValues() {
            TblSchema schema = TblSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();

            TblRow tblRow = TblRow.of(schema, 1, "2", 3);

            Assertions.assertEquals(schema, tblRow.getSchema());
        }

        @Test
        @DisplayName("스키마와 값이 주어지면 TblRow 객체 생성")
        public void testOfWithSchemaAndListValues() {
            TblSchema schema = TblSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", 3);

            TblRow tblRow = TblRow.of(schema, values);

            Assertions.assertEquals(schema, tblRow.getSchema());
            values.forEach(value -> Assertions.assertTrue(tblRow.toList().contains(value)));
        }

        @Test
        @DisplayName("스키마에 맞지 않은 값이 들어오면 예외 발생")
        void testOfWithSchemaAndListValues_invalidValues() {
            TblSchema schema = TblSchema.builder()
                    .addColumn("col1", Integer.class)
                    .addColumn("col2", String.class)
                    .addColumn("col3", Integer.class)
                    .build();
            List<Object> values = Arrays.asList(1, "2", "3");

            Assertions.assertThrows(IllegalArgumentException.class, () -> TblRow.of(schema, values));
        }
    }
}