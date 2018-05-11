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

package org.garnishtest.maven_plugins.pause;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>
 *     Pauses the execution of Maven.
 * </p>
 *
 * <p>
 *     <strong>Example usage:</strong>
 *     This is useful for example, when you start a servlet container (Tomcat, Jetty, etc.) from Maven before the tests
 *     so that the tests will run against this server. In this case it is useful to pause the execution of Maven just
 *     after the server started, so that you can run the tests in the IDE against this already-started server.
 * </p>
 */
@Mojo(
        name = "pause",
        defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST,
        threadSafe = true
)
public final class PauseMojo extends AbstractMojo {

    /**
     * If this system property exists and has a value of "true", Maven execution will be paused.
     */
    @Parameter(
            property = "pausePlugin.pauseSystemProperty",
            defaultValue = "pauseBeforeTests"
    )
    private String pauseSystemProperty;

    /**
     * The message to log just before pausing the Maven execution.
     */
    @Parameter(
            property = "pausePlugin.pauseMessage",
            defaultValue = "====================[ pausing before tests (to keep servers started) for testing in the IDE ]===================="
    )
    private String pauseMessage;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (shouldPause()) {
            getLog().info(pauseMessage);
            LockSupport.park();
        }
    }

    private boolean shouldPause() {
        final String propertyValue = System.getProperty(pauseSystemProperty);

        return "true".equals(propertyValue);
    }
}
