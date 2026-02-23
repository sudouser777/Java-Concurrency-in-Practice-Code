package chapter05;

import chapter02.Servlet;
import chapter02.ServletRequest;
import chapter02.ServletResponse;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;

@ThreadSafe
public class Factorizer implements Servlet {

    private final Computable<BigInteger, BigInteger[]> c = this::factor;

    private final Computable<BigInteger, BigInteger[]> cache = new Memorizer<>(c);

    @Override
    public void service(ServletRequest req, ServletResponse res) {
        try {
            BigInteger i = extractFromRequest(req);
            encodeIntoResponse(res, cache.compute(i));
        } catch (InterruptedException e) {
            encodeIntoResponse(res, "factorization interrupted");
        }

    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.ONE;
    }

    private BigInteger[] factor(BigInteger num) {
        return new BigInteger[]{num};
    }

    private void encodeIntoResponse(ServletResponse res, Object factors) {

    }
}
