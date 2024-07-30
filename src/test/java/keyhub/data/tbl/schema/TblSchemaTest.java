package keyhub.data.tbl.schema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}