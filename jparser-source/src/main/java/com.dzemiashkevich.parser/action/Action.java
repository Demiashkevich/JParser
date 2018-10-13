package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public interface Action {

    Map<String, List<Resource>> doAction(Deque<Rule> rules);

}
