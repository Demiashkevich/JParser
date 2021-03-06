package com.dzemiashkevich.parser.api;

import com.dzemiashkevich.parser.action.Action;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule {

    private String name;
    private Action action;
    private List<String> source;
    private Map<Selector, String> pattern = new HashMap<>();
    private Map<String, Pair<String, Integer>> param = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public Map<Selector, String> getPattern() {
        return pattern;
    }

    public void setPattern(Map<Selector, String> pattern) {
        this.pattern = pattern;
    }

    public Map<String, Pair<String, Integer>> getParam() {
        return param;
    }

    public void setParam(Map<String, Pair<String, Integer>> param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
