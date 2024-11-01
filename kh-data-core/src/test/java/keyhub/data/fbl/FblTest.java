package keyhub.data.fbl;

import keyhub.data.column.Column;
import keyhub.data.row.Row;
import keyhub.data.schema.Schema;
import keyhub.data.tbl.Tbl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FblTest {

    @Nested
    class ConstructorTest{
        @Test
        public void testFromTbl() {
            // given
            List<Column> schemas = List.of(
                    Column.of("column1", String.class),
                    Column.of("column2", String.class),
                    Column.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            Schema schema = Schema.from(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);

            // when
            Fbl result = Fbl.from(tblInstance);

            // then
            assertNotNull(result);
            assertEquals(tblInstance.count(), result.toList().size());
        }

        @Test
        public void testFromWhenEmptyTbl() {
            // given
            List<Column> schemas = List.of(
                    Column.of("column1", String.class),
                    Column.of("column2", String.class),
                    Column.of("column3", String.class)
            );
            Schema schema = Schema.from(schemas);
            Tbl tblInstance = Tbl.empty(schema);

            // when
            Fbl result = Fbl.from(tblInstance);

            // then
            assertNotNull(result);
            assertTrue(result.toList().isEmpty());
        }

        @Test
        public void testFromWhenNullTbl() {
            // expect
            assertThrows(NullPointerException.class, () -> Fbl.from(null));
        }

        @Test
        public void testOf(){
            // given
            List<Column> schemas = List.of(
                    Column.of("column1", String.class),
                    Column.of("column2", String.class),
                    Column.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            Schema schema = Schema.from(schemas);
            Tbl tblInstance = Tbl.of(schema, inputData);
            Stream<Row> rows = tblInstance.stream();

            // when
            Fbl result = Fbl.of(schema, rows);

            // then
            assertNotNull(result);
            assertEquals(tblInstance.count(), result.toList().size());
        }
    }
}