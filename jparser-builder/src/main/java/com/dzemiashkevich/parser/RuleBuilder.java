package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.action.JumpAction;
import com.dzemiashkevich.parser.action.TakeAction;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RuleBuilder {

    private final JumpAction jump;

    private final TakeAction take;

    @Autowired
    public RuleBuilder(JumpAction jump, TakeAction take) {
        this.jump = jump;
        this.take = take;
    }

    @PostConstruct
    public void build() {
        ArrayDeque<Rule> rules = new ArrayDeque<Rule>();

        HashMap<Select, String> pattern = new HashMap<Select, String>();
        pattern.put(Select.OUTER, "#content table tbody tr td a");
//        pattern.put(Select.INNER, "tr td a");
        pattern.put(Select.HREF, "href");

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

        HashMap<Select, String> patternTake = new HashMap<Select, String>();
        patternTake.put(Select.OUTER, "#content");
//        pattern.put(Select.INNER, "tr td a");
//        patternTake.put(Select.HREF, "href");

        Map<String, String> param = new HashMap<String, String>();
        param.put("name", "div h1.page-header");
        param.put("description", "div p");
        param.put("price", "div div div span span");

        Rule rule4 = new Rule();
        rule4.setAction(take);
        rule4.setPattern(patternTake);
        rule4.setParam(param);
        rule4.setSource(new ArrayList<>(Collections.singletonList("http://www.dealmed.ru/uzi_skaner_i6.html")));

        rules.addLast(rule1);
        rules.addLast(rule2);
        rules.addLast(rule3);
        rules.addLast(rule4);

        jump.doAction(rules);
    }

}
