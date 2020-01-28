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

package org.garnishtest.modules.generic.springutils;

public final class ClasspathUtilsException extends RuntimeException {

    private static final long serialVersionUID = -6935809292660056737L;

    public ClasspathUtilsException() {
    }

    public ClasspathUtilsException(final String message) {
        super(message);
    }

    public ClasspathUtilsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClasspathUtilsException(final Throwable cause) {
        super(cause);
    }

    public ClasspathUtilsException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
