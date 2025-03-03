import java.math.BigInteger

class Chapter3Problem5 {
    fun countInv(input: IntArray): BigInteger {
        return if (input.size < 2) BigInteger.ZERO else {
            val leftInversionCount = countInv(input.take(input.size / 2).toIntArray())
            val rightInversionCount = countInv(input.drop(input.size / 2).toIntArray())
            val splitInversionCount = countSplitInv(input)
            return leftInversionCount + rightInversionCount + splitInversionCount
        }
    }

    private fun countSplitInv(input: IntArray): BigInteger {
        val firstHalf = input.take(input.size / 2).sorted()
        val lastHalf = input.drop(input.size / 2).sorted()
        var splitInvCount = BigInteger.ZERO

        var j = 0
        var k = 0
        for (i in input.indices) {
            if (firstHalf[j] > lastHalf[k]) {
                k++
                splitInvCount += BigInteger.valueOf(firstHalf.size.toLong() - j)
                if (k > lastHalf.lastIndex) break
            } else {
                j++
                if (j > firstHalf.lastIndex) break
            }
        }

        return splitInvCount
    }
}
