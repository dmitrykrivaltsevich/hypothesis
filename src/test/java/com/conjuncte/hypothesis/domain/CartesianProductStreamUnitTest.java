package com.conjuncte.hypothesis.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CartesianProductStreamUnitTest {

    @Test
    public void testStreamValues() throws Exception {
        // Given
        Stream<Hypothesis> stream = new CartesianProductStream(new Pair<Register, Register>() {
            @Override
            public Register getFirst() {
                return new FreeRadixRegister("1", 10);
            }

            @Override
            public Register getSecond() {
                return new FreeRadixRegister("1", 10);
            }
        }, 1);

        // When
        Collection<Hypothesis> hypothesises = new ArrayList<Hypothesis>();
        while (stream.hasNext()) {
            hypothesises.add(stream.next());
        }

        Collection<Register> registers = new ArrayList<Register>();
        for (Hypothesis hypothesis : hypothesises) {
            registers.add(hypothesis.getFactors().getFirst());
            registers.add(hypothesis.getFactors().getSecond());
        }

        // Then
        assertEquals(200, registers.size());
    }
}