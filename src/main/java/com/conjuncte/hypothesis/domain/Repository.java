package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.Stream;

import javax.annotation.Nonnull;

public interface Repository<T> {

    boolean isEmpty();

    @Nonnull
    T get();

    void add(@Nonnull Stream<T> stream);

    int size();
}
