package com.conjuncte.hypothesis.domain;

import java.math.BigInteger;

public class FreeRadixRegister
        implements Register {

    private final BigInteger product;

    private FreeRadixRegister(String product, int radix) {
        assert product != null;
        assert radix >= Character.MIN_RADIX && radix <= Character.MAX_RADIX;

        this.product = new BigInteger(product, radix);
    }
}
