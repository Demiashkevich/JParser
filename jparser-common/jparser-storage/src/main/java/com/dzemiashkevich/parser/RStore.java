package com.dzemiashkevich.parser;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RStore {

    private Map<String, List<Resource>> allResource = new HashMap<>();

    public boolean save(String name, List<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return true;
        }


        if (this.allResource.containsKey(name)) {
            List<Resource> myResource = this.allResource.get(name);
            myResource.addAll(resources);
            return true;
        }

        this.allResource.put(name, resources);
        return true;
    }

    public Map<String, List<Resource>> fetch() {
        return allResource;
    }

}
