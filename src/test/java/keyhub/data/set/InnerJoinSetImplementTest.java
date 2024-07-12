package keyhub.data.set;

import keyhub.data.DataSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class InnerJoinSetImplementTest {

    @Test
    public void testComputeJoinRawResult() {
        // Prepare data sets for test
        DataSet dataSet1 = DataSet.of(Arrays.asList("id", "name"));
        dataSet1.addRow(Arrays.asList(1, "John Doe"));
        dataSet1.addRow(Arrays.asList(2, "Jane Doe"));

        DataSet dataSet2 = DataSet.of(Arrays.asList("id", "email"));
        dataSet2.addRow(Arrays.asList(1, "john.doe@email.com"));
        dataSet2.addRow(Arrays.asList(3, "mary.jane@email.com"));

        InnerJoinSetImplement innerJoinSet = new InnerJoinSetImplement(dataSet1, dataSet2);
        innerJoinSet.on("id").selectAll();

        // Execute the method under test
        DataSet rawResult = innerJoinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult.getRows());
        // [[1, John Doe, 1, john.doe@email.com]]
        assertEquals(1, rawResult.size());
        assertEquals(4, rawResult.getRow(0).size());
    }

    @Test
    public void testComputeJoinRawResultWhenNoMatches() {
        // Prepare data sets for test
        DataSet dataSet1 = DataSet.of(Arrays.asList("id", "name"));
        dataSet1.addRow(Arrays.asList(1, "John Doe"));
        dataSet1.addRow(Arrays.asList(2, "Jane Doe"));

        DataSet dataSet2 = DataSet.of(Arrays.asList("id", "email"));
        dataSet2.addRow(Arrays.asList(3, "mary.jane@email.com"));

        InnerJoinSetImplement innerJoinSet = new InnerJoinSetImplement(dataSet1, dataSet2);
        innerJoinSet.on("id").selectAll();
        // Execute the method under test
        DataSet rawResult = innerJoinSet.computeJoinRawResult();

        // Verify the results are correct
        assertEquals(0, rawResult.size());
    }

    @Test
    @DisplayName("one left two right join")
    public void testComputeJoinRawResultWhenOneLeftTwoRightJoin() {
        // Prepare data sets for test
        DataSet dataSet1 = DataSet.of(Arrays.asList("id", "name"));
        dataSet1.addRow(Arrays.asList(1, "John Doe"));
        dataSet1.addRow(Arrays.asList(2, "Jane Doe"));

        DataSet dataSet2 = DataSet.of(Arrays.asList("id", "age"));
        dataSet2.addRow(Arrays.asList(1, 10));
        dataSet2.addRow(Arrays.asList(1, 20));
        dataSet2.addRow(Arrays.asList(2, 10));

        InnerJoinSetImplement joinSet = new InnerJoinSetImplement(dataSet1, dataSet2);
        joinSet.on("id").selectAll();
        // Execute the method under test
        DataSet rawResult = joinSet.computeJoinRawResult();

        // Verify the results are correct
        System.out.println(rawResult.getRows());
        // [[1, John Doe, 1, 10], [1, John Doe, 1, 20], [2, Jane Doe, 2, 10]]
        assertEquals(3, rawResult.size());
    }
}