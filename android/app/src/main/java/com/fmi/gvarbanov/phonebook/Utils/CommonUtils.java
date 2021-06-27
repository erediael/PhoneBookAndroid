package com.fmi.gvarbanov.phonebook.Utils;

public class CommonUtils {
    public static boolean isNullOrWhiteSpace(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }
}
