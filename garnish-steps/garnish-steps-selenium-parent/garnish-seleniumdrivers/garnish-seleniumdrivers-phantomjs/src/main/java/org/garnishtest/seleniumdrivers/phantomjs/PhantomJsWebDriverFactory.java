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

package org.garnishtest.seleniumdrivers.phantomjs;

import org.garnishtest.seleniumdrivers.phantomjs.server.PhantomJsServer;
import org.openqa.selenium.Platform;
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
        final DesiredCapabilities capabilities = new DesiredCapabilities("phantomjs", "", Platform.ANY);
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
