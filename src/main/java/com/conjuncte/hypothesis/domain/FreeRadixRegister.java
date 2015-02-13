package com.conjuncte.hypothesis.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        BigInteger product = karatsuba(number, ((FreeRadixRegister) target).number);
//        BigInteger product = number.multiply(((FreeRadixRegister) target).number);
        return new FreeRadixRegister(product.toString(), radix);
    }

    @Override
    public boolean hasCell(Integer cellOffset) {
        return number.toString(radix).length() > cellOffset;
    }

    @Override
    public Cell getCell(final Integer cellOffset) {
        // not optimal here
        String numberAsString = number.toString(radix);
        return createCell(Integer.valueOf(numberAsString.substring(
                numberAsString.length() - cellOffset - 1,
                numberAsString.length() - cellOffset)));
    }

    @Override
    public Integer getCapacity() {
        return number.toString(radix).length();
    }

    @Override
    public Collection<Cell> getPossibleCellValues() {
        // todo: quick and dirty
        List<Cell> possibleValues = new ArrayList<Cell>();
        possibleValues.add(createCell(1));
        possibleValues.add(createCell(2));
        possibleValues.add(createCell(3));
        possibleValues.add(createCell(4));
        possibleValues.add(createCell(5));
        possibleValues.add(createCell(6));
        possibleValues.add(createCell(7));
        possibleValues.add(createCell(8));
        possibleValues.add(createCell(9));
// todo: buggy
//        possibleValues.add(createCell(0));
        return possibleValues;
    }

    @Override
    public String toString() {
        return number.toString(radix);
    }

    @Override
    public boolean equals(Object aThat) {
        if (this == aThat) return true;
        if (!(aThat instanceof FreeRadixRegister)) return false;
        FreeRadixRegister that = (FreeRadixRegister) aThat;

        return number.equals(that.number);
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
        if (N <= 2000) return x.multiply(y);                // optimize this parameter

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
