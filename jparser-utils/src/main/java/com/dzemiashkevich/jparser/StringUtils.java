package com.dzemiashkevich.jparser;

import com.dzemiashkevich.parser.ApplicationException;
import com.dzemiashkevich.parser.TypeException;
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

        int count = 0;

        while (matcher.find()) {
            int indexOfStart = matcher.start();
            int indexOfEnd = matcher.end();

            if (indexOfStart <= indexOfDifference && indexOfEnd >= indexOfDifference) {
                MatcherUtils.save(count);
                return str2.substring(0, indexOfStart) + "%s" + str2.substring(indexOfEnd);
            }

            count++;
        }

        throw new RuntimeException(String.format("Unable to generate template by urls: %s, %s", str1, str2));
    }

    public static String template(Pair<String, String> pair1) throws ApplicationException {
        String str1 = pair1.getLeft();

        Integer count;
        try {
            count = MatcherUtils.fetch();
        } catch (ApplicationException exception) {
            throw new ApplicationException(TypeException.TEMPLATE_GENERATION, str1);
        }

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(pair1.getLeft());

        while (matcher.find()) {
            if (count-- == 0) {
                int indexOfStart = matcher.start();
                int indexOfEnd = matcher.end();

                return str1.substring(0, indexOfStart) + "%s" + str1.substring(indexOfEnd);
            }
        }

        throw new ApplicationException(TypeException.MATCHER_FINDER, str1);
    }

}
