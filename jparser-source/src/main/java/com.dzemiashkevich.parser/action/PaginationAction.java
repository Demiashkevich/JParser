package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PaginationAction extends JumpAction {

    @Override
    protected List<String> parseContextBySelect(Document document, Map<Selector, String> select) {
        Elements context = document.select(select.get(Selector.OUTER));

        Element from = context.first();
        String _fromHref = from.attr(select.get(Selector.HREF));
        String _fromText = from.text();

        Element to = context.last();
        String _toHref = to.attr(select.get(Selector.HREF));
        String _toText = to.text();

        if (StringUtils.isEmpty(_fromHref) || StringUtils.isEmpty(_toHref)) {
            return Collections.emptyList();
        }

        return generateHref(Pair.of(_fromHref, _fromText), Pair.of(_toHref, _toText));
    }

    @Override
    protected void prepareRuleToProcessing(Rule prev, Rule next, List<String> href) {

    }

    private List<String> generateHref(Pair<String, String> from, Pair<String, String> to) {
        String hrefTemplate = com.dzemiashkevich.jparser.StringUtils.template(from, to);

        if (StringUtils.isEmpty(hrefTemplate)) {
            return Collections.emptyList();
        }

        List<String> href = new ArrayList<>();

        for (int i = Integer.parseInt(from.getValue()); i <= Integer.parseInt(to.getValue()); i++) {
            href.add(String.format(hrefTemplate, i));
        }

        return href;
    }

}
