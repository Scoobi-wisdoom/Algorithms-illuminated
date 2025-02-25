import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.math.BigInteger
import kotlin.test.assertEquals

class Chapter1Problem6Test {
    private val kotlinSolutionSuite = Chapter1Problem6()
    private val javaSolutionSuite = JavaChapter1Problem6()

    @Test
    fun `Karatsuba multiplication on zero`() {
        val x = "0"
        val y = "100"

        assertAll(
            { assertEquals(BigInteger("0"), kotlinSolutionSuite.doKaratsubaMultiplication(x, y)) },
            { assertEquals(BigInteger("0"), javaSolutionSuite.doKaratsubaMultiplication(x, y)) },
        )
    }

    @Test
    fun `Karatsuba multiplication on one`() {
        val x = "1"
        val y = "999"

        assertAll(
            { assertEquals(BigInteger("999"), kotlinSolutionSuite.doKaratsubaMultiplication(x, y)) },
            { assertEquals(BigInteger("999"), javaSolutionSuite.doKaratsubaMultiplication(x, y)) },
        )
    }

    @Test
    fun `Karatsuba multiplication on different digit length inputs`() {
        val x = "120"
        val y = "12"

        assertAll(
            { assertEquals(BigInteger("1440"), kotlinSolutionSuite.doKaratsubaMultiplication(x, y)) },
            { assertEquals(BigInteger("1440"), javaSolutionSuite.doKaratsubaMultiplication(x, y)) },
        )
    }

    @Test
    fun `Karatsuba multiplication on odd number digit inputs`() {
        val x = "123"
        val y = "123"

        assertAll(
            { assertEquals(BigInteger("15129"), kotlinSolutionSuite.doKaratsubaMultiplication(x, y)) },
            { assertEquals(BigInteger("15129"), javaSolutionSuite.doKaratsubaMultiplication(x, y)) },
        )
    }

    @Test
    fun `Karatsuba multiplication on four-digit inputs`() {
        val x = "1234"
        val y = "5678"

        assertAll(
            { assertEquals(BigInteger("7006652"), kotlinSolutionSuite.doKaratsubaMultiplication(x, y)) },
            { assertEquals(BigInteger("7006652"), javaSolutionSuite.doKaratsubaMultiplication(x, y)) },
        )
    }

    @Test
    fun `Karatsuba multiplication on inputs given in the problem`() {
        val x = "3141592653589793238462643383279502884197169399375105820974944592"
        val y = "2718281828459045235360287471352662497757247093699959574966967627"

        assertAll(
            {
                assertEquals(
                    BigInteger("8539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184"),
                    kotlinSolutionSuite.doKaratsubaMultiplication(x, y),
                )
            },
            {
                assertEquals(
                    BigInteger("8539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184"),
                    javaSolutionSuite.doKaratsubaMultiplication(x, y),
                )
            },
        )
    }
}
