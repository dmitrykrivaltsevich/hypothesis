package com.conjuncte.hypothesis.container;

import javax.annotation.Nonnull;

public interface Pair<F, S> {

    @Nonnull
    F getFirst();

    @Nonnull
    S getSecond();
}
