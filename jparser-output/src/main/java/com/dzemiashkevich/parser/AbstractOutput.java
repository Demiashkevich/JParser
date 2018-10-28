package com.dzemiashkevich.parser;

import java.util.List;
import java.util.Map;

public abstract class AbstractOutput implements Output {
    public abstract void write(Map<String, List<Resource>> resources);
}
