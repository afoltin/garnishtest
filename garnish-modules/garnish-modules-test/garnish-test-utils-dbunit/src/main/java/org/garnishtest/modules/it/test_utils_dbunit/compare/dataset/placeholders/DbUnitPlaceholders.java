package org.garnishtest.modules.it.test_utils_dbunit.compare.dataset.placeholders;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class DbUnitPlaceholders {

    private static final String PLACEHOLDER_START = "%{";
    private static final String PLACEHOLDER_END = "}";

    private final Map<String, String> values = new HashMap<>();

    /**
     * @return true if and only if a new placeholder has been recorded
     */
    public boolean recordPlaceholderIfPossible(@Nullable final String possiblePlaceholder,
                                               @Nullable final String value) {

        final String placeholderName = parsePlaceholderName(possiblePlaceholder);
        if (placeholderName == null) {
            // not a placeholder
            return false;
        }

        if (this.values.containsKey(placeholderName)) {
            // placeholder already recorded
            return false;
        }

        this.values.put(placeholderName, value);

        return true;
    }


    @Nullable
    public String replacePlaceholders(@Nullable final String possiblePlaceholder) {
        if (possiblePlaceholder == null) {
            return null;
        }

        final String placeholderName = parsePlaceholderName(possiblePlaceholder);
        if (placeholderName == null) {
            // not a placeholder
            return possiblePlaceholder;
        }

        return this.values.get(placeholderName);
    }

    @Nullable
    private static String parsePlaceholderName(@Nullable final String text) {
        if (text == null) {
            return null;
        }
        if (!(text.startsWith(PLACEHOLDER_START) && text.endsWith(PLACEHOLDER_END))) {
            return null;
        }

        return text.substring(PLACEHOLDER_START.length(), text.length() - PLACEHOLDER_END.length());
    }

}
