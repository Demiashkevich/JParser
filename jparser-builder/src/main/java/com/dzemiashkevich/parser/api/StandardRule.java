package com.dzemiashkevich.parser.api;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class StandardRule {

    private String name;
    private String actionType;
    private String source;
    private Map<Selector, String> pattern = new HashMap<>();
    private Map<String, Pair<String, String>> param = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<Selector, String> getPattern() {
        return pattern;
    }

    public void setPattern(Map<Selector, String> pattern) {
        this.pattern = pattern;
    }

    public Map<String, Pair<String, String>> getParam() {
        return param;
    }

    public void setParam(Map<String, Pair<String, String>> param) {
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
