import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.math.BigInteger
import kotlin.test.assertEquals

class Chapter3Problem5Test {
    private val kotlinSolutionSuite = Chapter3Problem5()
    private val javaSolutionSuite = JavaChapter3Problem5()

    @Test
    fun sanityCheck() {
        val intArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val sortedInput = intArray.sorted().toIntArray()
        val reverseSortedInput = intArray.sortedDescending().toIntArray()

        assertAll(
            { assertEquals(BigInteger.ZERO, kotlinSolutionSuite.countInv(sortedInput)) },
            { assertEquals(BigInteger.valueOf(28), kotlinSolutionSuite.countInv(reverseSortedInput)) },

            { assertEquals(BigInteger.ZERO, javaSolutionSuite.countInv(sortedInput)) },
            { assertEquals(BigInteger.valueOf(28), javaSolutionSuite.countInv(reverseSortedInput)) },
        )
    }

    @Test
    fun authorTestCase1() {
        val given = intArrayOf(
            54044,
            14108,
            79294,
            29649,
            25260,
            60660,
            2995,
            53777,
            49689,
            9083,
        )

        assertAll(
            { assertEquals(BigInteger.valueOf(28), kotlinSolutionSuite.countInv(given)) },
            { assertEquals(BigInteger.valueOf(28), javaSolutionSuite.countInv(given)) },
        )
    }

    @Test
    fun authorTestCase2() {
        val given = Helper.readIntArray("problem3.5.txt")

        assertAll(
            { assertEquals(BigInteger.valueOf(2_407_905_288), kotlinSolutionSuite.countInv(given)) },
            { assertEquals(BigInteger.valueOf(2_407_905_288), javaSolutionSuite.countInv(given)) },
        )
    }
}
