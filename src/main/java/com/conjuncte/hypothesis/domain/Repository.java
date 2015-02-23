package com.conjuncte.hypothesis.domain;

import javax.annotation.Nonnull;

public interface Repository<T> {

    boolean isEmpty();

    @Nonnull
    T get();

    void add(@Nonnull T data);
}
