package com.dzemiashkevich.parser.builder;

import com.dzemiashkevich.parser.DStore;
import com.dzemiashkevich.parser.action.Action;
import com.dzemiashkevich.parser.action.ClickAction;
import com.dzemiashkevich.parser.action.PaginationAction;
import com.dzemiashkevich.parser.action.TakeAction;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.StandardRule;
import com.dzemiashkevich.parser.api.Selector;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class RuleBuilder implements Builder<Rule, StandardRule> {

    private final DStore documentStore;
    private final ClickAction click;
    private final TakeAction take;
    private final PaginationAction pagination;

    @Autowired
    public RuleBuilder(ClickAction click, TakeAction take, PaginationAction pagination, DStore documentStore) {
        this.click = click;
        this.take = take;
        this.pagination = pagination;
        this.documentStore = documentStore;
    }

    @Override
    public Deque<Rule> build(List<StandardRule> standard) {
        Deque<Rule> rules = new ArrayDeque<>();

        if (CollectionUtils.isEmpty(standard)) {
            return rules;
        }

        for (StandardRule s : standard) {

            if (StringUtils.isEmpty(s.getSource()) || CollectionUtils.isEmpty(s.getPattern())) {
                continue;
            }

            Rule rule = buildRule(s);

            if (ObjectUtils.isEmpty(rule)) {
                rules.add(rule);
            }

        }

        return rules;
    }

    private Rule buildRule(StandardRule standard) {
        Document document = prepareDocument(standard.getSource());
        Elements context = prepareContext(document, standard.getPattern());

        Map<String, Pair<String, Integer>> params = prepareParams(context, standard.getParam());
        Action action = prepareActionType(standard.getActionType());

        Rule rule = new Rule();

        rule.setSource(Collections.singletonList(standard.getSource()));
        rule.setPattern(standard.getPattern());
        rule.setParam(params);
        rule.setAction(action);

        return rule;
    }

    private Document prepareDocument(String source) {
        return documentStore.fetch(source);
    }

    private Elements prepareContext(Document document, Map<Selector, String> pattern) {
        return document.select(pattern.get(Selector.OUTER));
    }

    private Map<String, Pair<String, Integer>> prepareParams(Elements context, Map<String, Pair<String, String>> standard) {
        Map<String, Pair<String, Integer>> params = new HashMap<>();

        if (CollectionUtils.isEmpty(standard)) {
            return params;
        }

        for (Map.Entry<String, Pair<String, String>> e : standard.entrySet()) {
            Map.Entry<String, Pair<String, Integer>> param = prepareParam(context, e);

            if (!ObjectUtils.isEmpty(param)) {
                params.put(param.getKey(), param.getValue());
            }
        }

        return params;
    }

    private Map.Entry<String, Pair<String, Integer>> prepareParam(Elements context, Map.Entry<String, Pair<String, String>> standard) {
        String selector = standard.getValue().getKey();
        String template = standard.getValue().getValue();
        String paramName = standard.getKey();
        Elements elements = context.select(selector);

        for (int index = 0; index < context.size(); index++) {
            Element element = elements.get(index);

            if (!ObjectUtils.isEmpty(element) && element.hasText()) {
                String text = element.text();
                if (text.equals(template)) {
                    return Map.entry(paramName, Pair.of(selector, index));
                }
            }

        }

        return null;
    }

    private Action prepareActionType(String actionType) {
        switch (actionType) {
            case "click" : return click;
            case "take" : return take;
            case "pagination" : return pagination;
            default: throw new RuntimeException("Unknown action type: " + actionType);
        }
    }

}
