package com.conjuncte.hypothesis.domain;

import java.math.BigInteger;

public class FreeRadixRegister
        implements Register {

    private final BigInteger number;
    private final int radix;

    public FreeRadixRegister(String number, int radix) {
        assert number != null;
        assert radix >= Character.MIN_RADIX && radix <= Character.MAX_RADIX;

        this.number = new BigInteger(number, radix);
        this.radix = radix;
    }

    @Override
    public Register partialProduct(Register target, Integer cellOffset) {
        // todo: dirty hack. don't want to implement partial product for now
        BigInteger product = number.multiply(((FreeRadixRegister) target).number);
        return new FreeRadixRegister(product.toString(), radix);
    }

    @Override
    public boolean hasCell(Integer cellOffset) {
        return number.toString(radix).length() > cellOffset;
    }

    @Override
    public Cell getCell(final Integer cellOffset) {
        return new Cell() {
            @Override
            public int getValue() {
                return number.toString(radix).charAt(cellOffset);
            }

            @Override
            public boolean equals(Object aThat) {
                if (this == aThat) return true;
                if (!(aThat instanceof Cell)) return false;
                Cell that = (Cell) aThat;

                return getValue() == that.getValue();
            }
        };
    }

    @Override
    public Integer getCapacity() {
        return number.toString(radix).length();
    }

    @Override
    public String toString() {
        return number.toString(radix);
    }
}
