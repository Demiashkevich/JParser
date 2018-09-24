package com.dzemiashkevich.parser.action;

import com.dzemiashkevich.parser.Resource;
import com.dzemiashkevich.parser.api.Rule;

import java.util.Deque;
import java.util.List;

public interface Action {

    List<Resource> doAction(Deque<Rule> rules);

}
