package com.conjuncte.hypothesis.container;

import javax.annotation.Nonnull;

public interface Stream<T>
        extends Comparable<Stream<T>> {

    boolean hasNext();

    @Nonnull
    T next();
}
