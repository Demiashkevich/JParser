package com.dzemiashkevich.parser;

import com.dzemiashkevich.parser.action.TakeAction;
import com.dzemiashkevich.parser.api.Rule;
import com.dzemiashkevich.parser.api.StandardRule;
import com.dzemiashkevich.parser.builder.RuleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.List;

@Component
public class ResourceManager {

    private final RuleBuilder builder;
    private final CsvOutput output;
    private final TakeAction take;

    @Autowired
    public ResourceManager(RuleBuilder builder, CsvOutput output, TakeAction take) {
        this.builder = builder;
        this.output = output;
        this.take = take;
    }

    public void process(List<StandardRule> standard) {
        Deque<Rule> rules = builder.build(standard);
        List<Resource> resources = take.doAction(rules);
        output.write(resources);
    }

}
