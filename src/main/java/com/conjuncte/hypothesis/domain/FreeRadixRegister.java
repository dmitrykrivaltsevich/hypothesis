package com.conjuncte.hypothesis.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FreeRadixRegister
        implements Register {

    private static final String ZERO = "0";

    private final String number;
    private final int radix;

    public FreeRadixRegister(String number, int radix) {
        assert number != null;
        assert radix >= Character.MIN_RADIX && radix <= Character.MAX_RADIX;

        this.number = number;
        this.radix = radix;
    }

    @Override
    public Register partialMultiply(Register target, Integer cellOffset) {
        // todo: dirty hack. don't want to implement partial product for now
        // todo: cast to FreeRadixRegister -- design issue in any case
//        BigInteger product = karatsuba(
//                new BigInteger(number, radix),
//                new BigInteger(((FreeRadixRegister) target).number, radix));

        BigInteger product = new BigInteger(number, radix)
                .multiply(new BigInteger(((FreeRadixRegister) target).number, radix));

        return new FreeRadixRegister(
                String.format("%1$" + number.length() + "s", product.toString()).replace(" ", ZERO),
                radix);
    }

    @Override
    public Cell getCell(final Integer cellOffset) {
        return createCell(Integer.valueOf(number.substring(
                number.length() - cellOffset - 1,
                number.length() - cellOffset)));
    }

    @Override
    public Integer getCapacity() {
        return number.length();
    }

    @Override
    public Collection<Cell> getPossibleCellValues() {
        // todo: quick and dirty
        List<Cell> possibleValues = new ArrayList<>();
        possibleValues.add(createCell(1));
        possibleValues.add(createCell(2));
        possibleValues.add(createCell(3));
        possibleValues.add(createCell(4));
        possibleValues.add(createCell(5));
        possibleValues.add(createCell(6));
        possibleValues.add(createCell(7));
        possibleValues.add(createCell(8));
        possibleValues.add(createCell(9));
        possibleValues.add(createCell(0));
        return possibleValues;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object aThat) {
        if (this == aThat) return true;
        if (!(aThat instanceof FreeRadixRegister)) return false;
        FreeRadixRegister that = (FreeRadixRegister) aThat;

        return number.equals(that.number) && radix == that.radix;
    }

    private Cell createCell(final int value) {
        return new Cell() {
            @Override
            public int getValue() {
                return value;
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

    /**
     * Multiply two positive N-bit BigIntegers using Karatsuba multiplication.
     *
     * @param x first multiplier
     * @param y second multiplier
     * @return product
     * @see http://introcs.cs.princeton.edu/java/78crypto/Karatsuba.java.html
     */
    private static BigInteger karatsuba(BigInteger x, BigInteger y) {

        // cutoff to brute force
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 8) return x.multiply(y);                // optimize this parameter

        // number of bits divided by 2, rounded up
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // compute sub-expressions
        BigInteger ac = karatsuba(a, c);
        BigInteger bd = karatsuba(b, d);
        BigInteger abcd = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2 * N));
    }
}
