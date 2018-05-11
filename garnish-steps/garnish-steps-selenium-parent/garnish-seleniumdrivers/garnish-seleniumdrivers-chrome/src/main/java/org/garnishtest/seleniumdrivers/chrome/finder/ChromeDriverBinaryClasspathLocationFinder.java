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

package org.garnishtest.seleniumdrivers.chrome.finder;

import org.garnishtest.seleniumdrivers.utils.OsUtils;
import org.apache.commons.lang3.SystemUtils;

public final class ChromeDriverBinaryClasspathLocationFinder {

    private static final String BASE_PATH = "garnishtest/seleniumdrivers/chrome";

    public String getClasspathLocationForCurrentOperatingSystem() {
        final boolean isWindows = OsUtils.isWindows();
        final boolean isLinux = OsUtils.isLinux();
        final boolean isMac = OsUtils.isMac();

        final boolean is64BitArchitecture = OsUtils.is64BitArchitecture();
        final boolean is32BitArchitecture = OsUtils.is32BitArchitecture();

        if (isWindows) {
            return BASE_PATH + "/windows/32bit/chromedriver-32bit-2.2.1.exe";
        } else if (isLinux) {
            if (is64BitArchitecture) {
                return BASE_PATH + "/linux/64bit/chromedriver-64bit-2.21";
            } else {
                if (is32BitArchitecture) {
                    return BASE_PATH + "/linux/32bit/chromedriver-32bit-2.21";
                } else {
                    throw new IllegalArgumentException(
                            "unsupported Linux architecture [" + OsUtils.getOsArchitecture() + "]"
                    );
                }
            }
        } else if (isMac) {
            return BASE_PATH + "/mac/32bit/chromedriver-32bit-2.2.1";
        } else {
            throw new IllegalArgumentException(
                    "unsupported operating system" +
                    "name=[" + SystemUtils.OS_NAME + "]" +
                    ", version=[" + SystemUtils.OS_VERSION + "]" +
                    ", architecture=[" + OsUtils.getOsArchitecture() + "]"
            );
        }
    }

}
