package keyhub.data.simpleimplement;

import keyhub.data.join.InnerJoinSetImplement;
import keyhub.data.tbl.Tbl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InnerJoinSetImplementTest {

    @Test
    public void testComputeJoinRawResult() {
        // Prepare data sets for test
        Tbl tbl1 = Tbl.builder()
                .addColumns(Arrays.asList("id", "name"))
                .addRow(Arrays.asList(1, "John Doe"))
                .addRow(Arrays.asList(2, "Jane Doe"))
                .build();

        Tbl tbl2 = Tbl.builder()
                .addColumns(Arrays.asList("id", "email"))
                .addRow(Arrays.asList(1, "john.doe@email.com"))
                .addRow(Arrays.asList(3, "mary.jane@email.com"))
                .build();

        InnerJoinSetImplement innerJoinSet = new InnerJoinSetImplement(tbl1, tbl2);
        innerJoinSet.on("id").selectAll();

        // Execute the method under test
        List<List<Object>> rawResult = innerJoinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult);
        // [[1, John Doe, 1, john.doe@email.com]]
        assertEquals(1, rawResult.size());
        assertEquals(4, rawResult.getFirst().size());
    }

    @Test
    public void testComputeJoinRawResultWhenNoMatches() {
        // Prepare data sets for test
        Tbl tbl1 = Tbl.builder()
                .addColumns(Arrays.asList("id", "name"))
                .addRow(Arrays.asList(1, "John Doe"))
                .addRow(Arrays.asList(2, "Jane Doe"))
                .build();
        Tbl tbl2 = Tbl.builder()
                .addColumns(Arrays.asList("id", "email"))
                .addRow(Arrays.asList(3, "mary.jane@email.com"))
                .build();

        InnerJoinSetImplement innerJoinSet = new InnerJoinSetImplement(tbl1, tbl2);
        innerJoinSet.on("id").selectAll();
        // Execute the method under test
        List<List<Object>> rawResult = innerJoinSet.computeJoinRawResult();

        // Verify the results are correct
        assertEquals(0, rawResult.size());
    }

    @Test
    @DisplayName("one left two right join")
    public void testComputeJoinRawResultWhenOneLeftTwoRightJoin() {
        // Prepare data sets for test
        Tbl tbl1 = Tbl.builder()
                .addColumns(Arrays.asList("id", "name"))
                .addRow(Arrays.asList(1, "John Doe"))
                .addRow(Arrays.asList(2, "Jane Doe"))
                .build();
        Tbl tbl2 = Tbl.builder()
                .addColumns(Arrays.asList("id", "age"))
                .addRow(Arrays.asList(1, 10))
                .addRow(Arrays.asList(1, 20))
                .addRow(Arrays.asList(2, 10))
                .build();

        InnerJoinSetImplement joinSet = new InnerJoinSetImplement(tbl1, tbl2);
        joinSet.on("id").selectAll();
        // Execute the method under test
        List<List<Object>> rawResult = joinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult);
        // [[1, John Doe, 1, 10], [1, John Doe, 1, 20], [2, Jane Doe, 2, 10]]
        assertEquals(3, rawResult.size());
    }
}