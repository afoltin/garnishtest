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

import org.garnishtest.modules.generic.function_value_matcher.FunctionValueMatcher;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.DbUnitDataSetComparer;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.DbUnitDataSetComparerException;
import org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.row_compare.DbUnitRowComparer;
import org.garnishtest.modules.it.test_utils_dbunit.compare.utils.DbUnitXmlDataSetUtils;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class DbUnitDataSetComparerTest {

    private static final boolean SHOULD_MATCH = true;
    private static final boolean SHOULD_NOT_MATCH = false;

    private static final String EXPECTED_FILE_NAME = "expected.db.xml";
    private static final String ACTUAL_FILE_NAME   = "actual.db.xml";

    private final String testRelativeDirectory;
    private final boolean datasetsMatch;

    private final DbUnitDataSetComparer comparer;

    public DbUnitDataSetComparerTest(final String testRelativeDirectory,
                                     final boolean datasetsMatch) {
        this.testRelativeDirectory = testRelativeDirectory;
        this.datasetsMatch = datasetsMatch;

        this.comparer = new DbUnitDataSetComparer(
                new DbUnitRowComparer(
                        new FunctionValueMatcher(
                                Collections.emptyList()
                        )
                )
        );
    }

    @Test
    public void test() throws Exception {
        final IDataSet expectedDataSet = DbUnitXmlDataSetUtils.loadFromResource(
                new ClassPathResource(this.testRelativeDirectory + "/" + EXPECTED_FILE_NAME, DbUnitDataSetComparerTest.class)
        );
        final IDataSet actualDataSet = DbUnitXmlDataSetUtils.loadFromResource(
                new ClassPathResource(this.testRelativeDirectory + "/" + ACTUAL_FILE_NAME, DbUnitDataSetComparerTest.class)
        );

        if (this.datasetsMatch) {
            this.comparer.compareDataSets(expectedDataSet, actualDataSet);
        } else {
            try {
                this.comparer.compareDataSets(expectedDataSet, actualDataSet);
                fail("datasets are different and it should have thrown [" + AssertionError.class.getSimpleName() + "]");
            } catch (final DbUnitDataSetComparerException ignore) {
                // ignore
            }
        }
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> parameters() {
        final List<Object[]> result = new ArrayList<>();

        result.addAll(Arrays.asList(
                new Object[]{"same_empty"                      , SHOULD_MATCH     },
                new Object[]{"same_not_empty"                  , SHOULD_MATCH     },
                new Object[]{"subset"                          , SHOULD_MATCH     },
                new Object[]{"subset_full_tables"              , SHOULD_MATCH     },
                new Object[]{"subset_full_tables_not_matching" , SHOULD_NOT_MATCH },
                new Object[]{"complex"                         , SHOULD_MATCH     },
                new Object[]{"complex_2"                       , SHOULD_MATCH     },
                new Object[]{"complex_not_matching"            , SHOULD_NOT_MATCH },
                new Object[]{"actual_row_matched_only_once"    , SHOULD_NOT_MATCH }
        ));

        return result;
    }

}