package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.jparser.StringUtils;
import com.dzemiashkevich.parser.ApplicationException;
import com.dzemiashkevich.parser.HStore;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.finder.DiapasonFinder;
import com.dzemiashkevich.parser.finder.api.Diapason;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PaginationAction extends JumpAction {

    private final DiapasonFinder finder;
    private final HStore hrefStore;

    @Autowired
    public PaginationAction(DiapasonFinder finder, HStore hrefStore) {
        this.finder = finder;
        this.hrefStore = hrefStore;
    }

    @Override
    protected List<String> parseContextBySelect(Document document, Map<Selector, String> select) {
        Diapason diapason = finder.doAction(select, document);

        if (ObjectUtils.isEmpty(diapason.getFrom()) && ObjectUtils.isEmpty(diapason.getTo())) {
            return new ArrayList<>(Collections.singletonList(document.location()));
        }



        return generateHref(diapason.getFrom(), diapason.getTo());
    }

    private List<String> process(Diapason diapason) {
        List<String> processedHref = generateHref(diapason.getFrom(), diapason.getTo());

        if (!hrefStore.isEmpty()) {
            List<Pair<String, String>> unprocessedPage = hrefStore.fetch();
            for (Pair<String, String> page : unprocessedPage) {
                try {
                    String template = StringUtils.template(page);
                    processedHref.add(template);
                } catch (ApplicationException e) {

                }
            }
        }

        return processedHref;
    }

    @Override
    protected void prepareRuleToProcessing(Rule prev, Rule next, List<String> href) {
        next.getSource().addAll(href);
        next.setSource(removeRepeatable(next.getSource()));
    }

    private List<String> generateHref(Pair<String, String> from, Pair<String, String> to) {
        String template;
        try {
            if (from.getLeft().equals(to.getLeft())) {
                template = com.dzemiashkevich.jparser.StringUtils.template(from);
                return generateHrefByTemplate(template, from.getRight(), from.getRight());
            } else {
                template = com.dzemiashkevich.jparser.StringUtils.template(from, to);
                return generateHrefByTemplate(template, from.getRight(), to.getRight());
            }
        } catch (ApplicationException exception) {
            hrefStore.save(from);
            return Collections.emptyList();
        }
    }

    private List<String> generateHrefByTemplate(String template, String from, String to) {
        List<String> href = new ArrayList<>();
        for (int i = 1; i <= Integer.parseInt(to); i++) {
            href.add(String.format(template, i));
        }
        return href;
    }

}
