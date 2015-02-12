package com.conjuncte.hypothesis.domain;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class InitialHypothesisStreamUnitTest {

    @Test
    public void testStreamValues() throws Exception {
        // Given
        Stream<Hypothesis> stream = new InitialHypothesisStream();

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
        assertEquals(Lists.newArrayList(
                new FreeRadixRegister("1", 10),
                new FreeRadixRegister("1", 10),
                new FreeRadixRegister("3", 10),
                new FreeRadixRegister("1", 10),
                new FreeRadixRegister("7", 10),
                new FreeRadixRegister("1", 10),
                new FreeRadixRegister("9", 10),
                new FreeRadixRegister("1", 10),
                new FreeRadixRegister("3", 10),
                new FreeRadixRegister("3", 10),
                new FreeRadixRegister("7", 10),
                new FreeRadixRegister("3", 10),
                new FreeRadixRegister("9", 10),
                new FreeRadixRegister("3", 10),
                new FreeRadixRegister("7", 10),
                new FreeRadixRegister("7", 10),
                new FreeRadixRegister("9", 10),
                new FreeRadixRegister("7", 10),
                new FreeRadixRegister("9", 10),
                new FreeRadixRegister("9", 10)
        ), registers);
    }
}