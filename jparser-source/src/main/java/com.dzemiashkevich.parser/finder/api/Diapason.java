package com.dzemiashkevich.parser.finder.api;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.ObjectUtils;

public class Diapason {

    private Pair<String, String> from;
    private Pair<String, String> to;

    public Diapason(Pair<String, String> from, Pair<String, String> to) {
        if (ObjectUtils.isEmpty(from) && !ObjectUtils.isEmpty(to)) {
            this.from = to;
            this.to = to;
            return;
        }
        if (ObjectUtils.isEmpty(to) && !ObjectUtils.isEmpty(from)) {
            this.to = from;
            this.from = from;
            return;
        }
        if (!ObjectUtils.isEmpty(to) && !ObjectUtils.isEmpty(from)) {
            this.from = from;
            this.to = to;
        }
    }

    public Pair<String, String> getFrom() {
        return from;
    }

    public void setFrom(Pair<String, String> from) {
        this.from = from;
    }

    public Pair<String, String> getTo() {
        return to;
    }

    public void setTo(Pair<String, String> to) {
        this.to = to;
    }

}
