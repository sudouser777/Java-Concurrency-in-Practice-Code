package chapter03;


import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {

    private BigInteger lastNumber;

    private BigInteger[] lastFactors;


    public OneValueCache(BigInteger number, BigInteger[] factors) {
        lastNumber = number;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger num) {
        if (lastNumber == null || !lastNumber.equals(num))
            return null;
        else
            return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}

