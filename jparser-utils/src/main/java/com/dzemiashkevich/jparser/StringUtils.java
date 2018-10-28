package com.dzemiashkevich.jparser;

import org.apache.commons.lang3.tuple.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    private StringUtils() {

    }

    public static String template(Pair<String, String> pair1, Pair<String, String> pair2) {
        String str1 = pair1.getLeft();
        String str2 = pair2.getLeft();

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
                MatcherUtils.save(matcher.groupCount());
                return str2.substring(0, indexOfStart) + "%s" + str2.substring(indexOfEnd);
            }
        }

        throw new RuntimeException(String.format("Unable to generate template by urls: %s, %s", str1, str2));
    }

    public static String template(Pair<String, String> pair1) {
        String str1 = pair1.getLeft();

        Integer groupId = MatcherUtils.fetch();

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(pair1.getLeft());

        String group = matcher.group(groupId);

        if (org.apache.commons.lang3.StringUtils.equals(group, pair1.getRight())) {
            int indexOfStart = matcher.start(groupId);
            int indexOfEnd = matcher.end(groupId);

            return str1.substring(0, indexOfStart) + "%s" + str1.substring(indexOfEnd);
        }

        throw new RuntimeException(String.format("Unable to generate template by url: %s", str1));
    }

}
