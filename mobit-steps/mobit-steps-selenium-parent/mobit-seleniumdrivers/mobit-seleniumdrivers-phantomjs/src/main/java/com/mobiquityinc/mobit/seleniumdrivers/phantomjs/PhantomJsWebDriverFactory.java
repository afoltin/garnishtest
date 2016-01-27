package com.mobiquityinc.mobit.seleniumdrivers.phantomjs;

import com.mobiquityinc.mobit.seleniumdrivers.phantomjs.server.PhantomJsServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public final class PhantomJsWebDriverFactory {

    public static WebDriver createWebDriver(final int port,
                                            @Nullable final String... additionalOptions) {
        try {
            return tryToCreateWebDriver(port, additionalOptions);
        } catch (final Exception e) {
            throw new PhantomJsWebDriverFactoryException(
                    "failed to create PhantomJS WebDriver" +
                    "; port=[" + port + "]" +
                    ", additionalOptions=[" + Arrays.toString(additionalOptions) + "]",
                    e
            );
        }
    }

    private static WebDriver tryToCreateWebDriver(final int port,
                                                  @Nullable final String... additionalOptions) throws MalformedURLException {
        startPhantomJsServer(port, additionalOptions);

        return createWebDriver(port);
    }

    private static WebDriver createWebDriver(final int port) throws MalformedURLException {
        final DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setJavascriptEnabled(true);

        final RemoteWebDriver remoteWebDriver = new RemoteWebDriver(new URL("http://localhost:" + port), capabilities);

        // augmenting allows us to take screenshots
        return new Augmenter().augment(remoteWebDriver);
    }

    private static void startPhantomJsServer(final int port,
                                             @Nullable final String... additionalOptions) {
        final PhantomJsServer phantomJsServer = PhantomJsServer.exeFromClasspath(port, additionalOptions);
        phantomJsServer.start();
    }

}
