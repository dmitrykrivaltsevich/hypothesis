package com.conjuncte.hypothesis.container;

import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.Register;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CartesianProductStreamUnitTest {

    @Test
    public void testStreamValues() throws Exception {
        // Given
        Stream<Hypothesis> stream = createStream("1", "1", 1);

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

    @Test
    public void testCompareToSatisfiesSignumContract() throws Exception {
        assertEquals(0, createStream("9", "9", 0).compareTo(createStream("8", "8", 0)));
        assertEquals(1, createStream("9", "9", 1).compareTo(createStream("9", "9", 0)));
        assertEquals(-1, createStream("9", "9", 0).compareTo(createStream("9", "9", 1)));

        assertEquals(0, createStream("144", "793", 0).compareTo(createStream("793", "155", 0)));
        assertEquals(1, createStream("123", "123", 0).compareTo(createStream("123", "133", 0)));
        assertEquals(-1, createStream("122", "144", 0).compareTo(createStream("123", "133", 0)));
    }

    private Stream<Hypothesis> createStream(final String firstNumber, final String secondNumber, int cellOffset) {
        return new CartesianProductStream(new Pair<Register, Register>() {
            @Override
            public Register getFirst() {
                return new FreeRadixRegister(firstNumber, 10);
            }

            @Override
            public Register getSecond() {
                return new FreeRadixRegister(secondNumber, 10);
            }
        }, cellOffset);
    }
}