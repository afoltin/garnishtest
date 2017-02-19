package com.mobiquityinc.mobit.seleniumdrivers.chrome;

import com.mobiquityinc.mobit.seleniumdrivers.chrome.finder.ChromeDriverBinaryClasspathLocationFinder;
import com.mobiquityinc.mobit.seleniumdrivers.utils.ClassPathExtractor;
import com.mobiquityinc.mobit.seleniumdrivers.utils.OsUtils;
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
