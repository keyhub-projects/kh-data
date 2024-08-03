package keyhub.data.tbl.schema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TblColumnSchemaTest {
    @Nested
    class CreateColumnSchemaTest {
        @Test
        @DisplayName("컬럼 스키마 제대로 만들어지나")
        void testConstructor() {
            String expectedColumnName = "testGetColumnName";
            Class<String> expectedColumnType = String.class;

            TblColumnSchema<?> tblColumnSchema = TblColumnSchema.of(expectedColumnName, expectedColumnType);

            assertEquals("testGetColumnName", tblColumnSchema.getColumnName());
            assertEquals(String.class, tblColumnSchema.getColumnType());
        }
    }

    @Nested
    @DisplayName("같은 객체")
    class EqualsTest {
        @Test
        void testEquals_sameObject() {
            TblColumnSchema<?> schema1 = TblColumnSchemaImplement.of("id", Integer.class);
            TblColumnSchema<?> schema2 = schema1;

            assertEquals(schema1, schema2);
        }

        @Test
        @DisplayName("같은 값을 가진 객체")
        void testEquals_equalObjects() {
            TblColumnSchema<?> schema1 = TblColumnSchemaImplement.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchemaImplement.of("id", Integer.class);

            assertEquals(schema1, schema2);
        }

        @Test
        @DisplayName("다른 값을 가진 객체")
        void testEquals_diffColumnName() {
            TblColumnSchema<?> schema1 = TblColumnSchemaImplement.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchemaImplement.of("name", Integer.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        @DisplayName("다른 타입을 가진 객체")
        void testEquals_diffColumnType() {
            TblColumnSchema<?> schema1 = TblColumnSchemaImplement.of("id", Integer.class);
            TblColumnSchema<?> schema2 = TblColumnSchemaImplement.of("id", String.class);

            Assertions.assertNotEquals(schema1, schema2);
        }

        @Test
        @DisplayName("null과 비교")
        void testEquals_compareToNull() {
            TblColumnSchema<?> schema1 = TblColumnSchemaImplement.of("id", Integer.class);

            Assertions.assertNotEquals(schema1, null);
        }
    }
}