package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Pair;
import com.conjuncte.hypothesis.domain.Register;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AppUnitTest {

    @Test
    public void testCanFindOneDigitFactorNumbers() {
        Pair<Register, Register> factors = new App().checkAllHypothesises(new FreeRadixRegister("49", 10));

        assertThat(factors, notNullValue());
        assertThat(factors.getFirst().toString(), equalTo("7"));
        assertThat(factors.getSecond().toString(), equalTo("7"));
    }
}