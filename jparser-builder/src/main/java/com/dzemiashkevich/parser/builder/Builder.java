package com.dzemiashkevich.parser.builder;

import java.util.Deque;
import java.util.List;

public interface Builder<M, D> {
    Deque<M> build(List<D> dto);
}
