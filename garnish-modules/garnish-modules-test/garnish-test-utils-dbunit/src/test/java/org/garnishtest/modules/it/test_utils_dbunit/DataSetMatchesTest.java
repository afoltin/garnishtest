/*
 * Copyright 2016-2018, Garnish.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.garnishtest.modules.it.test_utils_dbunit;

import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.model.match.DataSetMatches;
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