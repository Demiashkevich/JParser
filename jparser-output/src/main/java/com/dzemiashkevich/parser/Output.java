package com.dzemiashkevich.parser;

import java.util.List;
import java.util.Map;

public interface Output {
    void write(Map<String, List<Resource>> resources);
}
