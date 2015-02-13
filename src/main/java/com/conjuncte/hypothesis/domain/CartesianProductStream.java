package com.conjuncte.hypothesis.domain;

import com.google.common.collect.Iterators;

import java.util.Collection;

public class CartesianProductStream
        implements Stream<Hypothesis> {

    private final Pair<Register, Register> registers;
    private final int cellOffset;

    private int firstFactorOffset = 0;
    private int secondFactorOffset = 0;

    public CartesianProductStream(Pair<Register, Register> registers, int cellOffset) {
        assert registers != null;
        assert registers.getFirst() != null;
        assert registers.getSecond() != null;
        assert cellOffset >= 0;

        this.registers = registers;
        this.cellOffset = cellOffset;
    }

    @Override
    public boolean hasNext() {
        return firstFactorOffset < registers.getFirst().getPossibleCellValues().size()
                && secondFactorOffset < registers.getSecond().getPossibleCellValues().size();
    }

    @Override
    public Hypothesis next() {
        Collection<Cell> possibleCellValuesFirstRegister = registers.getFirst().getPossibleCellValues();
        Collection<Cell> possibleCellValuesSecondRegister = registers.getSecond().getPossibleCellValues();

        HypothesisImpl hypothesis = new HypothesisImpl(
                new FreeRadixRegister(
                        Iterators.get(possibleCellValuesFirstRegister.iterator(), firstFactorOffset).getValue()
                                + registers.getFirst().toString(), 10),
                new FreeRadixRegister(
                        Iterators.get(possibleCellValuesSecondRegister.iterator(), secondFactorOffset).getValue()
                                + registers.getSecond().toString(), 10),
                cellOffset);

        if (++firstFactorOffset == possibleCellValuesFirstRegister.size()) {
            secondFactorOffset += 1;
            firstFactorOffset = 0;
        }

        return hypothesis;
    }

    @Override
    public int compareTo(Stream<Hypothesis> stream) {
        CartesianProductStream aThat = (CartesianProductStream) stream;
        return Integer.valueOf(cellOffset).compareTo(aThat.cellOffset);
    }
}
