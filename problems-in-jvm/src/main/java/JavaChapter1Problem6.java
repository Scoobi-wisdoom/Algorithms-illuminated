import java.math.BigInteger;

public class JavaChapter1Problem6 {
    public BigInteger doKaratsubaMultiplication(String x, String y) {
        assert x.charAt(0) != '-' && y.charAt(0) != '-';
        if (x.length() == 1 && y.length() == 1) {
            return new BigInteger(x).multiply(new BigInteger(y));
        } else {
            final int max = Math.max(x.length(), y.length());
            final int evenLength = max % 2 == 0 ? max : max + 1;
            final String xPadded = String.format("%" + evenLength + "s", x).replace(' ', '0');
            final String yPadded = String.format("%" + evenLength + "s", y).replace(' ', '0');

            final String a = xPadded.substring(0, evenLength / 2);
            final String b = xPadded.substring(evenLength / 2);
            final String c = yPadded.substring(0, evenLength / 2);
            final String d = yPadded.substring(evenLength / 2);

            final BigInteger ac = doKaratsubaMultiplication(a, c);
            final BigInteger bd = doKaratsubaMultiplication(b, d);
            final BigInteger aPlusB_times_cPlusD = new BigInteger(a).add(new BigInteger(b))
                    .multiply(new BigInteger(c).add(new BigInteger(d)));

            return BigInteger.TEN.pow(evenLength).multiply(ac)
                    .add(
                            BigInteger.TEN.pow(evenLength / 2).multiply(aPlusB_times_cPlusD.subtract(ac).subtract(bd))
                    ).add(bd);
        }
    }
}
