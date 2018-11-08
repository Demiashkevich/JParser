package com.dzemiashkevich.parser.finder;

import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.finder.api.Diapason;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Component
public class DiapasonFinder implements Finder {
    @Override
    public Diapason doAction(Map<Selector, String> selectors, Document document) {
        Elements context = document.select(selectors.get(Selector.OUTER));

        Pair<String, String> from = findFrom(context, selectors);
        Pair<String, String> to = findTo(context, selectors);

        return new Diapason(from, to);
    }

    private Pair<String, String> findFrom(Elements context, Map<Selector, String> selectors) {
        if (selectors.containsKey(Selector.PAGINATION_CONTEXT)) {
            context = context.select(selectors.get(Selector.PAGINATION_CONTEXT));
        }
        Element from = context.first();

        if (ObjectUtils.isEmpty(from)) {
            return null;
        }

        return Pair.of(from.attr(selectors.get(Selector.HREF)), from.text());
    }

    private Pair<String, String> findTo(Elements context, Map<Selector, String> selectors) {
        Pair<String, String> to = null;

        if (selectors.containsKey(Selector.PAGINATION_TO)) {
            context = context.select(selectors.get(Selector.PAGINATION_TO));
            to = findTo(context, selectors.get(Selector.HREF));

            if (!ObjectUtils.isEmpty(to)) {
                return to;
            }
        }


        if (selectors.containsKey(Selector.PAGINATION_CONTEXT)) {
            context = context.select(selectors.get(Selector.PAGINATION_CONTEXT));
            to = findTo(context, selectors.get(Selector.HREF));

            if (!ObjectUtils.isEmpty(to)) {
                return to;
            }
        }

        return to;
    }

    private Pair<String, String> findTo(Elements context, String hrefSelector) {
        if (ObjectUtils.isEmpty(context) || context.isEmpty()) {
            return null;
        }

        Element to = context.last();

        if (ObjectUtils.isEmpty(to)) {
            return null;
        }

        return Pair.of(to.attr(hrefSelector), to.text());
    }
}
