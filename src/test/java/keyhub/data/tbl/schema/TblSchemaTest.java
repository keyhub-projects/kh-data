package keyhub.data.tbl.schema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TblSchemaTest {

    @Nested
    class CreateSchemaTest {
        @Test
       @DisplayName("컬럼 스키마가 테이블 스키마에 제대로 들어가고 나오나")
        void testConstructor() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testConstructor", String.class);
            List<TblColumnSchema> schemas = List.of(firstColumn);

            TblSchema tblSchema = TblSchema.of(schemas);

            assertEquals(1, tblSchema.getColumnSize());
            // 정상적으로 TblColumnSchema 나오나
            TblColumnSchema<?> testColumnSchema = tblSchema.getColumnSchema(0);
            assertEquals("testConstructor", testColumnSchema.getColumnName());
            assertEquals(String.class, testColumnSchema.getColumnType());
        }
    }

    @Nested
    class findColumnSchemaTest {
        @Test
        @DisplayName("컬럼 이름으로 찾아지나")
        void testFindColumnSchema() {
            TblColumnSchema<?> firstColumn = TblColumnSchema.of("testFindColumnSchema", String.class);
            List<TblColumnSchema> schemas = List.of(firstColumn);

            TblSchema tblSchema = TblSchema.of(schemas);

            Optional<TblColumnSchema<?>> result = tblSchema.findColumnSchema("testFindColumnSchema");
            assertNotNull(result.orElse(null));}
    }


    @Test
    void testEquals() {
        TblColumnSchema<?> firstColumn = TblColumnSchema.of("testEquals1", String.class);
        TblColumnSchema<?> secondColumn = TblColumnSchema.of("testEquals2", String.class);
        List<TblColumnSchema> schemas = List.of(firstColumn, secondColumn);
        TblSchema tblSchema1 = TblSchema.of(schemas);
        TblSchema tblSchema2 = TblSchema.of(schemas);

        assertEquals(tblSchema1, tblSchema2);
    }

    @Test
    void testNotEquals() {
        TblColumnSchema<?> firstColumn = TblColumnSchema.of("testEquals1", String.class);
        TblColumnSchema<?> secondColumn = TblColumnSchema.of("testEquals2", String.class);
        List<TblColumnSchema> schemas1 = List.of(firstColumn, secondColumn);
        TblSchema tblSchema1 = TblSchema.of(schemas1);

        List<TblColumnSchema> schemas2 = List.of(firstColumn);
        TblSchema tblSchema2 = TblSchema.of(schemas2);

        assertNotEquals(tblSchema1, tblSchema2);
    }
}