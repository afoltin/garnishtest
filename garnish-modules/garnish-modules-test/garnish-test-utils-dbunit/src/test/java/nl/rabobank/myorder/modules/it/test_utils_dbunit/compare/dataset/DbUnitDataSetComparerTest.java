package nl.rabobank.myorder.modules.it.test_utils_dbunit.compare.dataset;

import com.mobiquityinc.mobit.modules.generic.function_value_matcher.FunctionValueMatcher;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.DbUnitDataSetComparer;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.DbUnitDataSetComparerException;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.dataset.row_compare.DbUnitRowComparer;
import com.mobiquityinc.mobit.modules.it.test_utils_dbunit.compare.utils.DbUnitXmlDataSetUtils;
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