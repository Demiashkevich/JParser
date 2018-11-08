package com.dzemiashkevich.parser;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.WeakHashMap;

@Component
public class DStore {

    private Map<String, Document> documents = new WeakHashMap<>();

    private final DocumentLoader loader;

    @Autowired
    public DStore(DocumentLoader loader) {
        this.loader = loader;
    }

    public Document fetch(String url) {
        if (documents.containsKey(url)) {
            return documents.get(url);
        }

        Document newDocument = loader.load(url);

        if (ObjectUtils.isEmpty(newDocument)) {
            return null;
        }

        documents.put(url, newDocument);
        return newDocument;
    }

}
