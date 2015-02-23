package com.conjuncte.hypothesis.domain;

import com.conjuncte.hypothesis.container.Pair;

public class HypothesisImpl
        implements Hypothesis {

    private final Pair<Register, Register> factors;
    private final Integer cellOffsetToCheck;

    public HypothesisImpl(final Register factorFirst, final Register factorSecond, Integer cellOffsetToCheck) {
        assert factorFirst != null;
        assert factorSecond != null;
        assert cellOffsetToCheck != null;

        this.factors = new Pair<Register, Register>() {
            @Override
            public Register getFirst() {
                return factorFirst;
            }

            @Override
            public Register getSecond() {
                return factorSecond;
            }
        };
        this.cellOffsetToCheck = cellOffsetToCheck;
    }

    @Override
    public Pair<Register, Register> getFactors() {
        return factors;
    }

    @Override
    public Integer getCellOffsetToCheck() {
        return cellOffsetToCheck;
    }
}
