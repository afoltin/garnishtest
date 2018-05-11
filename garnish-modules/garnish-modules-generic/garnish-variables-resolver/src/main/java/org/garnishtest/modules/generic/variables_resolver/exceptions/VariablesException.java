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

package org.garnishtest.modules.generic.variables_resolver.exceptions;

import lombok.NonNull;

public class VariablesException extends RuntimeException {

    @NonNull private final String errorMessage;
    private final int errorIndex;
    @NonNull private final String text;

    @NonNull private final String fullErrorMessage;

    public VariablesException(@NonNull final String errorMessage,
                              final int errorIndex,
                              @NonNull final String text) {
        this.errorMessage = errorMessage;
        this.errorIndex = errorIndex;
        this.text = text;

        this.fullErrorMessage = createFullErrorMessage(errorMessage, errorIndex, text);
    }

    @NonNull
    private String createFullErrorMessage(@NonNull final String errorMessage,
                                          final int errorIndex,
                                          @NonNull final String text) {
        final StringBuilder result = new StringBuilder();

        result.append("\n");

        result.append(text).append("\n");

        // after snippet, error index is off
        for (int i = 0; i < errorIndex; i++) {
            result.append(' ');
        }
        result.append("^ ").append(errorMessage).append("; errorIndex=[").append(errorIndex).append("]");

        return result.toString();
    }

    public String getText() {
        return this.text;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getErrorIndex() {
        return this.errorIndex;
    }

    @Override
    public String getMessage() {
        return this.fullErrorMessage;
    }

}
