package org.garnishtest.seleniumdrivers.phantomjs.server.finder;

import org.garnishtest.seleniumdrivers.utils.OsUtils;
import org.apache.commons.lang3.SystemUtils;

public final class PhantomJsClasspathLocationFinder {

    private static final String BASE_PATH = "garnishtest/seleniumdrivers/phantomjs";

    public String getClasspathLocationForCurrentOperatingSystem() {
        final boolean isWindows = OsUtils.isWindows();
        final boolean isLinux = OsUtils.isLinux();
        final boolean isMac = OsUtils.isMac();

        final boolean is64BitArchitecture = OsUtils.is64BitArchitecture();
        final boolean is32BitArchitecture = OsUtils.is32BitArchitecture();

        if (isWindows) {
            return BASE_PATH + "/windows/32bit/phantomjs-32bit-2.1.1.exe";
        } else if (isLinux) {
            if (is64BitArchitecture) {
                return BASE_PATH + "/linux/64bit/phantomjs-64bit-2.1.1";
            } else {
                if (is32BitArchitecture) {
                    return BASE_PATH + "/linux/32bit/phantomjs-32bit-2.1.1";
                } else {
                    throw new IllegalArgumentException(
                            "unsupported Linux architecture [" + OsUtils.getOsArchitecture() + "]"
                    );
                }
            }
        } else if (isMac) {
            if (is64BitArchitecture) {
                return BASE_PATH + "/mac/64bit/phantomjs-64bit-2.1.1";
            } else {
                throw new IllegalArgumentException(
                        "unsupported Mac architecture [" + OsUtils.getOsArchitecture() + "]"
                );
            }
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
