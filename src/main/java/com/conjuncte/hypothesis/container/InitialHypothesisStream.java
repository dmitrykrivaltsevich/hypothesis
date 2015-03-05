package com.conjuncte.hypothesis.container;

import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.HypothesisImpl;

import javax.annotation.Nonnull;

public class InitialHypothesisStream
        implements Stream<Hypothesis> {

    private int[] possibleValues = new int[]{1, 3, 7, 9};
    private int firstFactorOffset = 0;
    private int secondFactorOffset = 0;

    public InitialHypothesisStream() {}

    public InitialHypothesisStream(@Nonnull int[] possibleValues) {
        // todo: create factory
        assert possibleValues != null;
        assert possibleValues.length > 0;

        this.possibleValues = possibleValues;
    }

    @Override
    public boolean hasNext() {
        return firstFactorOffset < possibleValues.length
                && secondFactorOffset < possibleValues.length;
    }

    @Override
    public Hypothesis next() {
        HypothesisImpl hypothesis = new HypothesisImpl(
                new FreeRadixRegister(String.valueOf(possibleValues[firstFactorOffset]), 10),
                new FreeRadixRegister(String.valueOf(possibleValues[secondFactorOffset]), 10),
                0);
        if (++firstFactorOffset == possibleValues.length) {
            secondFactorOffset += 1;
            firstFactorOffset = secondFactorOffset;
        }

        return hypothesis;
    }

    @Override
    public int compareTo(Stream<Hypothesis> stream) {
        return 1;
    }
}
