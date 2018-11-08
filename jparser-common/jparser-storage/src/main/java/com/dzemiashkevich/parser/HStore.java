package com.dzemiashkevich.parser;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HStore {

    private List<Pair<String, String>> pair = new ArrayList<>();

    public boolean isEmpty() {
        return pair.isEmpty();
    }

    public List<Pair<String, String>> fetch() {
        return pair;
    }

    public boolean save(Pair<String, String> href) {
        return pair.add(href);
    }

}
