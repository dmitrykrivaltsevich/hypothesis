package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.Pair;

import javax.annotation.Nonnull;

public interface Hypothesis {

    @Nonnull
    Pair<Register, Register> getFactors();

    @Nonnull
    Integer getCellOffsetToCheck();

}
