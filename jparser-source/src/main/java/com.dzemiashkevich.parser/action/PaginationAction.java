package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class PaginationAction extends JumpAction {

    @Override
    protected List<String> parseContextBySelect(Document document, Map<Selector, String> select) {
        Elements context = document.select(select.get(Selector.OUTER));
        Pair<String, String> from = parseRange(context, select.get(Selector.PAGINATION_FROM), select.get(Selector.HREF));
        Pair<String, String> to = parseRange(context, select.get(Selector.PAGINATION_TO), select.get(Selector.HREF));

        if (ObjectUtils.isEmpty(to)) {
            to = parseRange(context, select.get(Selector.HREF));
        }

        if (ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to)) {
            return new ArrayList<>(Collections.singletonList(document.location()));
        }

        return generateHref(from, to);
    }

    @Override
    protected void prepareRuleToProcessing(Rule prev, Rule next, List<String> href) {
        next.getSource().addAll(href);
        next.setSource(removeRepeatable(next.getSource()));
    }

    private Pair<String, String> parseRange(Elements context, String hrefSelector) {
        Element to = context.last();
        return Pair.of(to.attr(hrefSelector), to.text());
    }

    private Pair<String, String> parseRange(Elements context, String paginationSelector, String hrefSelector) {
        Elements range = context.select(paginationSelector);
        if (range.isEmpty()) {
            return null;
        }
        return Pair.of(range.attr(hrefSelector), range.text());
    }

    private List<String> generateHref(Pair<String, String> from, Pair<String, String> to) {
        String template;

        if (from.getLeft().equals(to.getLeft())) {
            template = com.dzemiashkevich.jparser.StringUtils.template(from);
        } else {
            template = com.dzemiashkevich.jparser.StringUtils.template(from, to);
        }

        return generateHrefByTemplate(template, from.getRight(), to.getRight());
    }

    private List<String> generateHrefByTemplate(String template, String from, String to) {
        List<String> href = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(to); i++) {
            href.add(String.format(template, i));
        }
        return href;
    }

}
