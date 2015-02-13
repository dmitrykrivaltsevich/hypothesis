package com.conjuncte.hypothesis.domain;

public interface Stream<T>
        extends Comparable<Stream<T>> {
    boolean hasNext();

    T next();
}
