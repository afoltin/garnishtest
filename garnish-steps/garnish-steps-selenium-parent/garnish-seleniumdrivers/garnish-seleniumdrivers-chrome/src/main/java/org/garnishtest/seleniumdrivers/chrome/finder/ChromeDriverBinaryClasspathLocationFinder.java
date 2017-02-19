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
