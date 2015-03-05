package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.container.Pair;
import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.LocalRepository;
import com.conjuncte.hypothesis.domain.Register;
import org.junit.Test;

import javax.annotation.Nonnull;

import static com.conjuncte.hypothesis.matcher.InPair.inPair;
import static org.hamcrest.CoreMatchers.both;
import static org.junit.Assert.assertThat;

public class AppUnitTest {

    @Test
    public void testCanFindOneDigitFactors() {
        assertThat(getFactors("49"), inPair("7"));
    }

    @Test
    public void testCanFindTwoDigitsFactors() throws Exception {
        assertThat(getFactors("121"), inPair("11"));
    }

    @Test
    public void testCanFindFactorsWithZeroDigits() throws Exception {
        assertThat(getFactors("10201"), inPair("101"));
    }

    @Test
    public void testCanFactorPseudoPrimesIntoFactorsOfEqualLength() throws Exception {
        assertThat(getFactors("282943"),        both(inPair("523")).and(inPair("541")));
        assertThat(getFactors("10051009"),      both(inPair("4721")).and(inPair("2129")));
        assertThat(getFactors("137920061"),     both(inPair("11903")).and(inPair("11587")));
        assertThat(getFactors("18202872433"),   both(inPair("124351")).and(inPair("146383")));
//        assertThat(getFactors("1858942564781"), both(inPair("1182787")).and(inPair("1571663")));
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("41893284683", 10).multiply(new BigInteger("31893284777", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1965478943", 10).multiply(new BigInteger("6985321517", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("122949829", 10).multiply(new BigInteger("122951513", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("12231257", 10).multiply(new BigInteger("12413887", 10)).toString(), 10);

    }

    private Pair<Register, Register> getFactors(String number) {
        final Pair<Register, Register>[] results = new Pair[1];
        new App.FactorsCalculator(
                new LocalRepository(),
                new FreeRadixRegister(number, 10),
                new App.CalculationStateCallback() {
                    @Override
                    public void onSuccess(@Nonnull Pair<Register, Register> factors) {
                        results[0] = factors;
                    }

                    @Override
                    public void onProgress(@Nonnull Pair<Register, Register> factors) {

                    }

                    @Override
                    public void onEnd() {

                    }
                }
        ).run();

        return results[0];
    }
}