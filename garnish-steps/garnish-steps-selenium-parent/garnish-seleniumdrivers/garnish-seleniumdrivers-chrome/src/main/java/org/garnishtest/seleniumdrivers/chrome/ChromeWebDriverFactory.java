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

package org.garnishtest.seleniumdrivers.chrome;

import org.garnishtest.seleniumdrivers.chrome.finder.ChromeDriverBinaryClasspathLocationFinder;
import org.garnishtest.seleniumdrivers.utils.ClassPathExtractor;
import org.garnishtest.seleniumdrivers.utils.OsUtils;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public final class ChromeWebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeWebDriverFactory.class);

    private ChromeWebDriverFactory() {
    }

    public static WebDriver createWebDriver() {
        final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled(true);

        final Path chromeBinaryTempPath = getChromeBinaryTempPath();
        System.setProperty("webdriver.chrome.driver", chromeBinaryTempPath.toAbsolutePath().toString());

        final WebDriver remoteWebDriver = new ChromeDriver();

        // augmenting allows us to take screenshots
        return new Augmenter().augment(remoteWebDriver);
    }

    @NonNull
    private static Path getChromeBinaryTempPath() {
        final ChromeDriverBinaryClasspathLocationFinder finder = new ChromeDriverBinaryClasspathLocationFinder();
        final ClassPathExtractor classPathExtractor = new ClassPathExtractor();

        final String classpathLocation = finder.getClasspathLocationForCurrentOperatingSystem();

        LOGGER.debug("Based on the current OS, Chrome driver binary is the classpath at [{}]; extracting to temp dir", classpathLocation);
        final Path fileToExecute = classPathExtractor.extractToTemp(classpathLocation, "chromedriver-");

        if (OsUtils.isLinux() || OsUtils.isMac()) {
            LOGGER.debug("Chrome driver binary extracted to [{}]; marking it as executable", fileToExecute.toAbsolutePath());

            final boolean success = fileToExecute.toFile().setExecutable(true);
            if (!success) {
                throw new IllegalArgumentException("failed to make extracted file executable");
            }
        }


        return fileToExecute;
    }

}
