package keyhub.data.stream;

import keyhub.data.column.KhColumn;
import keyhub.data.row.KhRow;
import keyhub.data.schema.KhSchema;
import keyhub.data.table.KhTable;
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
            List<KhColumn> schemas = List.of(
                    KhColumn.of("column1", String.class),
                    KhColumn.of("column2", String.class),
                    KhColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            KhSchema schema = KhSchema.from(schemas);
            KhTable tblInstance = KhTable.of(schema, inputData);

            // when
            KhStream result = KhStream.from(tblInstance);

            // then
            assertNotNull(result);
            assertEquals(tblInstance.count(), result.toList().size());
        }

        @Test
        public void testFromWhenEmptyTbl() {
            // given
            List<KhColumn> schemas = List.of(
                    KhColumn.of("column1", String.class),
                    KhColumn.of("column2", String.class),
                    KhColumn.of("column3", String.class)
            );
            KhSchema schema = KhSchema.from(schemas);
            KhTable tblInstance = KhTable.empty(schema);

            // when
            KhStream result = KhStream.from(tblInstance);

            // then
            assertNotNull(result);
            assertTrue(result.toList().isEmpty());
        }

        @Test
        public void testFromWhenNullTbl() {
            // expect
            assertThrows(NullPointerException.class, () -> KhStream.from(null));
        }

        @Test
        public void testOf(){
            // given
            List<KhColumn> schemas = List.of(
                    KhColumn.of("column1", String.class),
                    KhColumn.of("column2", String.class),
                    KhColumn.of("column3", String.class)
            );
            List<List<Object>> inputData = List.of(
                    List.of("A", "B", "C"),
                    List.of("D", "E", "F"),
                    List.of("G", "H", "I")
            );
            KhSchema schema = KhSchema.from(schemas);
            KhTable tblInstance = KhTable.of(schema, inputData);
            Stream<KhRow> rows = tblInstance.stream();

            // when
            KhStream result = KhStream.of(schema, rows);

            // then
            assertNotNull(result);
            assertEquals(tblInstance.count(), result.toList().size());
        }
    }
}