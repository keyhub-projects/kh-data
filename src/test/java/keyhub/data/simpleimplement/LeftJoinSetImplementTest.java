package keyhub.data.simpleimplement;

import keyhub.data.join.LeftJoinSetImplement;
import keyhub.data.tbl.Tbl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LeftJoinSetImplementTest {

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

        LeftJoinSetImplement leftJoinSet = new LeftJoinSetImplement(tbl1, tbl2);
        leftJoinSet.on("id").selectAll();

        // Execute the method under test
        var rawResult = leftJoinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult);
        // [[1, John Doe, 1, john.doe@email.com], [2, Jane Doe, null, null]]
        assertEquals(2, rawResult.size());
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
        LeftJoinSetImplement leftJoinSet = new LeftJoinSetImplement(tbl1, tbl2);
        leftJoinSet.on("id").selectAll();
        // Execute the method under test
        var rawResult = leftJoinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult);
        // [[1, John Doe, null, null], [2, Jane Doe, null, null]]
        assertEquals(2, rawResult.size());
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
                .addColumns(Arrays.asList("id", "email"))
                .addRow(Arrays.asList(1, 10))
                .addRow(Arrays.asList(1, 20))
                .addRow(Arrays.asList(2, 10))
                .build();

        LeftJoinSetImplement leftJoinSet = new LeftJoinSetImplement(tbl1, tbl2);
        leftJoinSet.on("id").selectAll();
        // Execute the method under test
        var rawResult = leftJoinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult);
        assertEquals(3, rawResult.size());
    }
}