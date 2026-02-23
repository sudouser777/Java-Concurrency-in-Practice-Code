package chapter02;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class SynchronizedFactorizer implements Servlet {

    @GuardedBy("this")
    private BigInteger lastNumber;

    @GuardedBy("this")
    private BigInteger[] lastFactors;

    @Override
    public synchronized void service(ServletRequest req, ServletResponse res) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(res, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
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

