package com.dzemiashkevich.jparser;

import com.dzemiashkevich.parser.ApplicationException;
import com.dzemiashkevich.parser.TypeException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class MatcherUtils {

    private static Map<Integer, Integer> weight = new HashMap<>();

    private MatcherUtils() {

    }

    public static boolean save(Integer groupId) {
        if (!weight.containsKey(groupId)) {
            weight.put(groupId, 1);
            return true;
        }
        weight.put(groupId, weight.get(groupId) + 1);
        return true;
    }

    public static Integer fetch() throws ApplicationException {
        if (weight.isEmpty()) {
            throw new ApplicationException(TypeException.GROUP_NOT_FOUND);
        }

        return Collections.max(weight.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public static boolean clear() {
        weight.clear();
        return true;
    }

}
