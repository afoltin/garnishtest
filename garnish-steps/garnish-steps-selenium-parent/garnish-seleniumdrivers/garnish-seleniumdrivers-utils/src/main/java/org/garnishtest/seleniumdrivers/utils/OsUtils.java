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
