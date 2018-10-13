package com.dzemiashkevich.jparser;

import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    private StringUtils() {

    }

    public static String template(Pair<String, String> pair1, Pair<String, String> pair2) {
        return difference(pair1.getLeft(), pair2.getLeft());
    }

    public static String difference(String str1, String str2) {
        if (str1.length() > str2.length()) {
            String temp = str1;
            str2 = str1;
            str1 = temp;
        }

        int indexOfDifference = org.apache.commons.lang3.StringUtils.indexOfDifference(str1, str2);

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(str2);

        while (matcher.find()) {
            int indexOfStart = matcher.start();
            int indexOfEnd = matcher.end();

            if (indexOfStart <= indexOfDifference && indexOfEnd >= indexOfDifference) {
                return str2.substring(0, indexOfStart + 1) + "%s" + str2.substring(indexOfEnd);
            }
        }

        throw new RuntimeException(String.format("Unable to generate template by urls: %s, %s", str1, str2));
    }

}
