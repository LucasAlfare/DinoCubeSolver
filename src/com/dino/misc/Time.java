package com.dino.misc;

import java.text.SimpleDateFormat;

//Auxiliar para debug de tempo.
public class Time {

    public static final String TIMESTAMP_PATTERN = "ss.SSS";
    public static final String TIMESTAMP_PATTERN_MINUTES = "mm:ss.SSS";

    private static final SimpleDateFormat SDF = new SimpleDateFormat();

    public static String timestampFor(long value) {
        SDF.applyPattern(getTimestampPattern(value));
        return SDF.format(value);
    }

    private static String getTimestampPattern(long value) {
        SDF.applyPattern("mm");
        return SDF.format(value).equals("00") ? TIMESTAMP_PATTERN : TIMESTAMP_PATTERN_MINUTES;
    }
}
