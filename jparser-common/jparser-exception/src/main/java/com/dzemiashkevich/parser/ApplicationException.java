package com.dzemiashkevich.parser;

public class ApplicationException extends Exception {

    public ApplicationException(TypeException type, Object... params) {
        super(String.format(type.getMessage(), params));
    }

}
