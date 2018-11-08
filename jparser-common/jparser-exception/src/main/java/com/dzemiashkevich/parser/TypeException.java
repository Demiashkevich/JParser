package com.dzemiashkevich.parser;

public enum TypeException {

    TEMPLATE_GENERATION("Unable generate template by following url %s"),
    GROUP_NOT_FOUND("Unable to successfully define group position"),
    MATCHER_FINDER("Unable find placeholder in the following url %s");

    private String message;

    TypeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
