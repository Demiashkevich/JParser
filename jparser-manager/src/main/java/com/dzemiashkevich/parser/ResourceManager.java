package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.action.ClickAction;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.StandardRule;
import com.dzemiashkevich.parser.builder.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.List;
import java.util.Map;

@Component
public class ResourceManager {

    private final RuleBuilder builder;
    private final CsvOutput output;
    private final ClickAction click;

    @Autowired
    public ResourceManager(RuleBuilder builder, CsvOutput output, ClickAction click) {
        this.builder = builder;
        this.output = output;
        this.click = click;
    }

    public void process(List<StandardRule> standard) {
        Deque<Rule> rules = builder.build(standard);
        Map<String, List<Resource>> resources = click.doAction(rules);
        output.write(resources);
    }

}
