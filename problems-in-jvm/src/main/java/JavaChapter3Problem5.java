import java.math.BigInteger;
import java.util.Arrays;

public class JavaChapter3Problem5 {
    public BigInteger countInv(int[] input) {
        if (input.length < 2) {
            return BigInteger.ZERO;
        } else {
            final BigInteger leftInversionCount = countInv(Arrays.copyOfRange(input, 0, input.length / 2));
            final BigInteger rightInversionCount = countInv(Arrays.copyOfRange(input, input.length / 2, input.length));
            final BigInteger splitInversionCount = countSplitInv(input);

            return leftInversionCount.add(rightInversionCount)
                    .add(splitInversionCount);
        }
    }

    private BigInteger countSplitInv(int[] input) {
        final int[] firstHalf = Arrays.copyOfRange(input, 0, input.length / 2);
        Arrays.sort(firstHalf);
        final int[] lastHalf = Arrays.copyOfRange(input, input.length / 2, input.length);
        Arrays.sort(lastHalf);

        BigInteger splitCount = BigInteger.ZERO;
        int j = 0;
        int k = 0;
        for (int i = 0; i < input.length; i++) {
            if (firstHalf[j] > lastHalf[k]) {
                k++;
                splitCount = BigInteger.valueOf(firstHalf.length - j).add(splitCount);
                if (k > lastHalf.length - 1) {
                    break;
                }
            } else {
                j++;
                if (j > firstHalf.length - 1) {
                    break;
                }
            }
        }

        return splitCount;
    }
}
