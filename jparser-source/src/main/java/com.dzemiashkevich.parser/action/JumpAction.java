package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.jparser.MatcherUtils;
import com.dzemiashkevich.parser.DStore;
import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class JumpAction implements Action {

    @Autowired
    protected DStore documentStore;

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

            List<String> href = this.parseContextBySelect(document, rule.getPattern());
            this.prepareRuleToProcessing(rule, rules.getFirst(), href);
        }

        MatcherUtils.clear();

        return rules.getFirst().getAction().doAction(rules);
    }

    protected List<String> removeRepeatable(List<String> source) {
        return source.stream().distinct().collect(Collectors.toList());
    }

    protected abstract List<String> parseContextBySelect(Document document, Map<Selector, String> pattern);
    protected abstract void prepareRuleToProcessing(Rule prev, Rule next, List<String> href);

}
