import java.math.BigInteger
import kotlin.math.max

class Chapter1Problem6 {
    fun doKaratsubaMultiplication(x: String, y: String): BigInteger {
        require(x.first() != '-' && y.first() != '-')
        return if (x.length == 1 && y.length == 1) {
            BigInteger(x) * BigInteger(y)
        } else {
            // Do not think of Karatsuba's final formula. Instead, think of number decomposition.
            val evenLength = max(x.length, y.length).let {
                if (it % 2 == 0) it else
                    it + 1
            }
            val (xPadded, yPadded) = x.padStart(evenLength, '0') to y.padStart(evenLength, '0')

            val a = xPadded.substring(0, xPadded.length / 2)
            val b = xPadded.substring(xPadded.length / 2)
            val c = yPadded.substring(0, yPadded.length / 2)
            val d = yPadded.substring(yPadded.length / 2)

            val ac = doKaratsubaMultiplication(a, c)
            val bd = doKaratsubaMultiplication(b, d)

            val `(a + b)(c + d)` = doKaratsubaMultiplication(
                BigInteger(a).plus(BigInteger(b)).toString(),
                BigInteger(c).plus(BigInteger(d)).toString(),
            )

            BigInteger.TEN.pow(evenLength) * ac + BigInteger.TEN.pow(evenLength / 2) * (`(a + b)(c + d)` - ac - bd) + bd
        }
    }
}
