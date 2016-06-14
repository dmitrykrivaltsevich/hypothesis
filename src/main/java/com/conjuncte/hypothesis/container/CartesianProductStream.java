package com.conjuncte.hypothesis.container;

import com.conjuncte.hypothesis.domain.*;
import com.google.common.collect.Iterators;

import java.util.Collection;

public class CartesianProductStream
        implements Stream<Hypothesis> {

    private final Pair<Register, Register> registers;
    private final int cellOffset;
    private final Integer sumOfSequences;

    private int firstFactorOffset = 0;
    private int secondFactorOffset = 0;

    public CartesianProductStream(Pair<Register, Register> registers, int cellOffset) {
        assert registers != null;
        assert registers.getFirst() != null;
        assert registers.getSecond() != null;
        assert cellOffset >= 0;

        this.registers = registers;
        this.cellOffset = cellOffset;
        this.sumOfSequences = countSequences(registers.getFirst().toString())
                + countSequences(registers.getSecond().toString());
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
        // todo: cast here - seems LSP violation here, need to re-think how to organize the code
        CartesianProductStream aThat = (CartesianProductStream) stream;

        // "Greater than" means all following rules:
        // 1. this.cellOffset > that.cellOffset
        // 2. this.registers.sumOfSequences < that.registers.sumOfSequences
        int cellOffsetComparison = Integer.valueOf(cellOffset).compareTo(aThat.cellOffset);
        return cellOffsetComparison == 0
                ? -sumOfSequences.compareTo(aThat.sumOfSequences)
                : cellOffsetComparison;
    }

    private int countSequences(String str) {
        int count = 0;
        count += countSubstring(str, "11");
        count += countSubstring(str, "22");
        count += countSubstring(str, "22");
        count += countSubstring(str, "33");
        count += countSubstring(str, "44");
        count += countSubstring(str, "55");
        count += countSubstring(str, "66");
        count += countSubstring(str, "77");
        count += countSubstring(str, "88");
        count += countSubstring(str, "99");
        count += countSubstring(str, "00");

        count += Math.abs(countSubstring(str, "12"));
        count += Math.abs(countSubstring(str, "13"));
        count += Math.abs(countSubstring(str, "21"));
        return count;

// todo: consider improvements
//        Map<String, Integer> counts = new HashMap<>();
//
//        for(String num : str.split("[0-9]{2}")) {
//            if (counts.get(num) == null) {
//                counts.put(num, 1);
//            }
//            counts.put(num, (counts.get(num) + 1) * (counts.get(num)));
//        }
//
//        int count = 0;
//        for (Integer value : counts.values()) {
//            count += value;
//        }
//
//        return count;
    }

    public int countSubstring(String str, String subStr) {
        int count = 0;
        for (int loc = str.indexOf(subStr); loc != -1; loc = str.indexOf(subStr, loc + 1)) {
            count++;
        }
        return count;
    }
}
