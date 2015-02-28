package com.conjuncte.hypothesis.matcher;

import com.conjuncte.hypothesis.container.Pair;
import com.conjuncte.hypothesis.domain.Register;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class InPair extends TypeSafeMatcher<Pair<Register, Register>> {

    private final String expectedValue;

    private InPair(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Factory
    public static Matcher<Pair<Register, Register>> inPair(String value) {
        return new InPair(value);
    }

    @Override
    protected boolean matchesSafely(Pair<Register, Register> pair) {
        return pair.getFirst().toString().equals(expectedValue)
                || pair.getSecond().toString().equals(expectedValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("in pair ").appendValue(expectedValue);
    }
}

