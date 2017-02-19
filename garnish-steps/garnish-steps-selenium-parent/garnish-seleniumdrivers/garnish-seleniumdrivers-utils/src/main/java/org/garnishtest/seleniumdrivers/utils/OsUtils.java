package org.garnishtest.seleniumdrivers.utils;

import org.apache.commons.lang3.SystemUtils;

public final class OsUtils {

    private OsUtils() { }

    public static boolean is64BitArchitecture() {
        final String osArchitecture = getOsArchitecture();

        return osArchitecture.equals("amd64")
               || osArchitecture.equals("x86_64")
               || osArchitecture.equals("i686");
    }

    public static boolean is32BitArchitecture() {
        final String osArchitecture = getOsArchitecture();

        return osArchitecture.equals("x86")
               || osArchitecture.equals("i386");
    }

    public static boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    public static boolean isMac() {
        return SystemUtils.IS_OS_MAC;
    }

    public static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
    }

    public static String getOsArchitecture() {
        return SystemUtils.OS_ARCH;
    }
}
