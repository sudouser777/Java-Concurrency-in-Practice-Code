package chapter02;


import net.jcip.annotations.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();

    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(res, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(res, factors);
        }
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.ONE;
    }

    private BigInteger[] factor(BigInteger num) {
        return new BigInteger[]{num};
    }

    private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {

    }

}

