package com.conjuncte.hypothesis.container;

import com.conjuncte.hypothesis.container.InitialHypothesisStream;
import com.conjuncte.hypothesis.container.Stream;
import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.Register;
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