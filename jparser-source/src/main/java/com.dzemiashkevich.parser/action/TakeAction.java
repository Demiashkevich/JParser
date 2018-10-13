package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.DStore;
import com.dzemiashkevich.parser.RStore;
import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TakeAction implements Action {

    private static final String EMPTY = "";

    private final RStore resourceStore;
    private final DStore documentStore;

    @Autowired
    public TakeAction(RStore resourceStore, DStore documentStore) {
        this.resourceStore = resourceStore;
        this.documentStore = documentStore;
    }

    @Override
    public Map<String, List<Resource>> doAction(Deque<Rule> rules) {
        if (CollectionUtils.isEmpty(rules)) {
            return Collections.emptyMap();
        }

        Rule rule = rules.removeFirst();

        List<String> source = rule.getSource();
        for (String res : source) {
            Document document = documentStore.fetch(res);

            if (document == null) {
                continue;
            }

            List<Resource> resources = parseDocumentByParamAndPattern(document, rule.getPattern(), rule.getParam());

            if (CollectionUtils.isEmpty(resources)) {
                continue;
            }

            resourceStore.save(rule.getName(), resources);
        }

        if (CollectionUtils.isEmpty(rules)) {
            return resourceStore.fetch();
        }

        Rule nextRule = rules.getFirst();
        prepareRuleToProcessing(nextRule, rule);
        return nextRule.getAction().doAction(rules);
    }

    private List<Resource> parseDocumentByParamAndPattern(Document document, Map<Selector, String> pattern, Map<String, Pair<String, Integer>> parameters) {
        Elements context = getContextFromDocument(document, pattern);

        if (context == null || context.isEmpty()) {
            return Collections.emptyList();
        }

        List<Resource> resources = new ArrayList<>();
        for (Element contextItem : context) {
            Resource res = getResourceFromElement(contextItem, parameters);
            if (!res.isEmpty()) {
                resources.add(res);
            }
        }

        return resources;
    }

    private Resource getResourceFromElement(Element element, Map<String, Pair<String, Integer>> parameters) {
        Resource resource = new Resource();

        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, Pair<String, Integer>> param : parameters.entrySet()) {
            String key = param.getKey();
            Pair<String, Integer> value = param.getValue();

            Elements elements = element.select(value.getKey());

            if (elements == null || elements.isEmpty()) {
                params.put(key, EMPTY);
                continue;
            }

            String content = elements.get(value.getValue()).text();

            params.put(key, content);
        }

        resource.setParams(params);

        return resource;
    }

    private Elements getContextFromDocument(Document document, Map<Selector, String> pattern) {
        return document.select(pattern.get(Selector.OUTER));
    }

    private void prepareRuleToProcessing(Rule next, Rule prev) {
        next.setSource(prev.getSource());
    }

}
