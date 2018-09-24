package com.dzemiashkevich.parser.api;

import com.dzemiashkevich.parser.action.Action;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule {

    private Action action;
    private List<String> source;
    private Map<Select, String> pattern = new HashMap<>();
    private Map<String, String> param = new HashMap<>();

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

    public Map<Select, String> getPattern() {
        return pattern;
    }

    public void setPattern(Map<Select, String> pattern) {
        this.pattern = pattern;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
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
