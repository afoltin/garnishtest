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

package org.garnishtest.demo.rest_complex.test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // packages to search for steps
        glue = {
                "org.garnishtest.demo.rest_complex.test",
                "org.garnishtest.steps"
        },

        // locations where to search for .feature files
        features = {
                "classpath:features"
        },

        // run everything that is not tagged with @ignore
        tags = {
                "~@ignore"
        },

        // put the test results to this json file
        // todo: pass a path that is not dependent on the current directory
        plugin = {
                "pretty",
                "json:garnish-demo-rest-complex-test/target/cucumber/cucumber-results.json"
        }
)
public final class IntegrationTests { }
