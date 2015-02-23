package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.Pair;

public interface Hypothesis {

    Pair<Register, Register> getFactors();

    Integer getCellOffsetToCheck();

}
