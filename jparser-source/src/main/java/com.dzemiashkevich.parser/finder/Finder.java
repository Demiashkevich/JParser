package com.dzemiashkevich.parser.finder;

import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.finder.api.Diapason;
import org.jsoup.nodes.Document;

import java.util.Map;

public interface Finder {

    Diapason doAction(Map<Selector, String> selectors, Document document);

}
