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

package org.garnishtest.modules.generic.function_value_matcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/spring/garnish-function-value-matcher.xml")
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