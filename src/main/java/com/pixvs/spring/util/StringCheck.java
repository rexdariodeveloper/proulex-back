package com.pixvs.spring.util;

public class StringCheck {
    public static boolean isNullorEmpty(final String s) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    public static String getValue(final String s) {
        // Null-safe, short-circuit evaluation.
        return isNullorEmpty(s) ? null : s.trim();
    }
}
