package com.dzemiashkevich.parser;

import java.util.List;

public abstract class AbstractOutput implements Output {
    public abstract void write(List<Resource> resources);
}
