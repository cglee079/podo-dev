package com.podo.pododev.backend.global.context;


import com.podo.pododev.core.util.DateTimeFormatUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ThreadLocalContext {

    private static ThreadLocal<Integer> debugIndex = new ThreadLocal<>();
    private static ThreadLocal<Integer> exceptionIndex = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Object>> values = new ThreadLocal<>();

    static {
        values.set(new HashMap<>());
        exceptionIndex.set(0);
        debugIndex.set(0);
    }

    public static void debug(String message) {
        Integer index = debugIndex.get();
        values.get().put("debug-" + index, DateTimeFormatUtil.toFullDateTime(LocalDateTime.now()) + " :: " + message);
        debugIndex.set(index + 1);
    }

    public static void put(String key, Object value) {
        values.get().put(key, value);
    }

    public static void putDateTime(String key, LocalDateTime localDateTime) {
        values.get().put(key, DateTimeFormatUtil.toFullDateTime(localDateTime));
    }

    public static void clear() {
        values.set(new HashMap<>());
        exceptionIndex.set(0);
        debugIndex.set(0);
    }

    public static Map<String, Object> toLog() {
        return new HashMap<>(values.get());
    }

    public static void init(String type) {
        Map<String, Object> valuesInit = new HashMap<>();
        valuesInit.put("id", type + "-" + UUID.randomUUID());
        valuesInit.put("type", type);
        values.set(valuesInit);
        exceptionIndex.set(0);
        debugIndex.set(0);
    }

    public static void putException(Exception e) {
        values.get().put("exceptionMessage-" + exceptionIndex.get(), e.getMessage());
        values.get().put("stackTrace-" + exceptionIndex.get(), Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        exceptionIndex.set(exceptionIndex.get() + 1);
    }


    public static String id() {
        return String.valueOf(values.get().get("id"));
    }
}
