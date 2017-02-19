package com.mobiquityinc.mobit.modules.generic.function_value_matcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring/mobit-function-value-matcher.xml")
public class FunctionValueMatcherTest {

    @Autowired
    private FunctionValueMatcher functionValueMatcher;

    @Test
    public void test_nullability() throws Exception {
        assertTrue(
                this.functionValueMatcher.matches(null, "isNull()")
        );
        assertFalse(
                this.functionValueMatcher.matches("not-null", "isNull()")
        );

        assertTrue(
                this.functionValueMatcher.matches("not-null", "isNotNull()")
        );
        assertFalse(
                this.functionValueMatcher.matches(null, "isNotNull()")
        );
    }

    @Test
    public void test_regex_matching() throws Exception {
        assertTrue(
                this.functionValueMatcher.matches(null, "matchesRegex(null)")
        );
        assertFalse(
                this.functionValueMatcher.matches("not-null", "matchesRegex(null)")
        );

        assertFalse(
                this.functionValueMatcher.matches(null, "matchesRegex(\"not-null\")")
        );
        assertTrue(
                this.functionValueMatcher.matches("Hello world!", "matchesRegex(\"Hello world!\")")
        );
        assertTrue(
                this.functionValueMatcher.matches("Hellooooooooo world!", "matchesRegex(\"Hello+ world!\")")
        );
        assertTrue(
                this.functionValueMatcher.matches("caSe DoeS Not maTTer", "matchesRegex(\"(?i)case does not matter\")")
        );

    }
}