package com.conjuncte.hypothesis.domain;

public interface Hypothesis {

    Pair<Register, Register> getFactors();

    Integer getCellOffsetToCheck();

}
