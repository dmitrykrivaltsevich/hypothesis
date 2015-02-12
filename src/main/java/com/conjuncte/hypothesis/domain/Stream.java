package com.conjuncte.hypothesis.domain;

public interface Stream<T> {
    boolean hasNext();

    T next();
}
