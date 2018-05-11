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

package org.garnishtest.maven_plugins.cucumber_reports;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(
        name = "generate-reports",
        defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST,
        threadSafe = true
)
public final class GenerateCucumberReportsMojo extends AbstractMojo {

    @Parameter(
            property = "cucumberReports.inputFile",
            defaultValue = "${project.build.directory}/cucumber/cucumber-results.json"
    )
    private File inputFile;

    @Parameter(
            property = "cucumberReports.outputDirectory",
            defaultValue = "${project.build.directory}/cucumber/cucumber-html-report"
    )
    private File outputDirectory;

    @Parameter(
            defaultValue = "${project}",
            required = true,
            readonly = true
    )
    private MavenProject project;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final boolean directoryCreated = outputDirectory.mkdirs();
        if (!directoryCreated) {
            throw new MojoExecutionException("failed to create directory [" + outputDirectory.getAbsolutePath() + "] or one of its parents");
        }

        final File reportOutputDirectory = this.outputDirectory;

        final List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(this.inputFile.getAbsolutePath());

        final String jenkinsBasePath = "";
        final String buildNumber = null;
        final String projectName = this.project.getName();
        final boolean failsIfSkipped = true;
        final boolean failsIfPending = false;
        final boolean failsIfUndefined = true;
        final boolean failsIfMissing = true;
        final boolean runWithJenkins = false;
        final boolean parallelTesting = false;

        final Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        // optionally only if you need
        configuration.setStatusFlags(failsIfSkipped, failsIfPending, failsIfUndefined, failsIfMissing);
        configuration.setParallelTesting(parallelTesting);
        configuration.setJenkinsBasePath(jenkinsBasePath);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

        final ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
