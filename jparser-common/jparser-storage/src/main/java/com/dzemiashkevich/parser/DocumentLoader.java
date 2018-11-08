package com.dzemiashkevich.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class DocumentLoader {

    Document load(final String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            return null;
        }
    }

}
