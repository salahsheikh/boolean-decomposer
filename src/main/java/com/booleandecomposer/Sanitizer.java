package com.booleandecomposer;

import java.util.HashMap;
import java.util.Map;

public class Sanitizer {

    private static Map<String, String> replacements = new HashMap<String, String>() {{
        put("1", "TRUE");
        put("0", "FALSE");
        put("~", "NOT");
        put("*", "AND");
        put("+", "OR");
        put("||", "OR");
        put("&&", "AND");
        put(".", "AND");
    }};

    /**
     * Replaces all variations of symbols with consistent symbols
     * @param raw Unaltered string.
     * @return Cleaned string with only one possible interpretation of operations
     */
    public static String cleanup(String raw) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            raw = raw.replace(entry.getKey(), entry.getValue());
        }
        return raw;
    }

}
