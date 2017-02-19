package nl.rabobank.myorder.modules.it.test_utils_dbunit.compare.dataset;

import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.model.match.DataSetMatches;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DataSetMatchesTest {

    @Test
    public void test() throws Exception {
        DataSetMatches dataSetMatches = new DataSetMatches();

        assertEquals(
                "[]",
                dataSetMatches.toString()
        );

        dataSetMatches = dataSetMatches.addRowMatch("P", 0, Arrays.asList(0, 1));
        assertEquals(
                "[(P:0-0)]\n" +
                "[(P:0-1)]",
                dataSetMatches.toString()
        );

        dataSetMatches = dataSetMatches.addRowMatch("P", 1, Collections.singletonList(2));
        assertEquals(
                "[(P:0-0), (P:1-2)]\n" +
                "[(P:0-1), (P:1-2)]",
                dataSetMatches.toString()
        );

        dataSetMatches = dataSetMatches.addRowMatch("A", 0, Collections.singletonList(3));
        assertEquals(
                "[(P:0-0), (P:1-2), (A:0-3)]\n" +
                "[(P:0-1), (P:1-2), (A:0-3)]",
                dataSetMatches.toString()
        );

        dataSetMatches = dataSetMatches.addRowMatch("A", 1, Arrays.asList(2, 4));
        assertEquals(
                "[(P:0-0), (P:1-2), (A:0-3), (A:1-2)]\n" +
                "[(P:0-0), (P:1-2), (A:0-3), (A:1-4)]\n" +
                "[(P:0-1), (P:1-2), (A:0-3), (A:1-2)]\n" +
                "[(P:0-1), (P:1-2), (A:0-3), (A:1-4)]",
                dataSetMatches.toString()
        );
    }

}