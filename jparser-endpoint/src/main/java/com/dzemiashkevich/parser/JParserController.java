package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Selector;
import com.dzemiashkevich.parser.api.StandardRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class JParserController {

    private final ResourceManager manager;

    @Autowired
    public JParserController(ResourceManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    public void init() {
        List<StandardRule> rules = new ArrayList<>();

        HashMap<Selector, String> pattern = new HashMap<>();
        pattern.put(Selector.OUTER, "#content table tbody tr td a");
        pattern.put(Selector.HREF, "href");

        Rule rule1 = new Rule();
        rule1.setAction(jump);
        rule1.setPattern(pattern);
        rule1.setSource(new ArrayList<>(Collections.singletonList("http://www.dealmed.ru")));

        Rule rule2 = new Rule();
        rule2.setAction(jump);
        rule2.setPattern(pattern);
        rule2.setSource(new ArrayList<>(Collections.singletonList("http://www.dealmed.ru/narkozno_dyxatelnye_apparaty_mindray.html")));

        Rule rule3 = new Rule();
        rule3.setAction(jump);
        rule3.setPattern(pattern);
        rule3.setSource(new ArrayList<>(Collections.singletonList("http://www.dealmed.ru/uzi_skanery.html")));

        HashMap<Selector, String> patternTake = new HashMap<>();
        patternTake.put(Selector.OUTER, "#content");

        Map<String, String> param = new HashMap<>();
        param.put("name", "div h1.page-header");
        param.put("description", "div > p");
        param.put("price", "div div div span.price span.price-value");

        Rule rule4 = new Rule();
        rule4.setAction(take);
        rule4.setPattern(patternTake);
        rule4.setParam(param);
        rule4.setSource(new ArrayList<>(Collections.singletonList("http://www.dealmed.ru/uzi_skaner_sonotouch10.html")));

        rules.addLast(rule1);
        rules.addLast(rule2);
        rules.addLast(rule3);
        rules.addLast(rule4);

        manager.process(rules);
    }

}
