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

package org.garnishtest.demo.rest_complex.web.service;

public final class AuthTokensServiceException extends RuntimeException {

    private static final long serialVersionUID = 8560860891333977732L;

    public AuthTokensServiceException() {
    }

    public AuthTokensServiceException(final String message) {
        super(message);
    }

    public AuthTokensServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthTokensServiceException(final Throwable cause) {
        super(cause);
    }

    public AuthTokensServiceException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
