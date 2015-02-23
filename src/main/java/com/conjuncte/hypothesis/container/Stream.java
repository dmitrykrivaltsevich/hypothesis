package com.conjuncte.hypothesis.container;

public interface Stream<T>
        extends Comparable<Stream<T>> {
    boolean hasNext();

    T next();
}
