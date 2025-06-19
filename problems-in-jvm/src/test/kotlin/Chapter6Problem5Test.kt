import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class Chapter6Problem5Test {
    private val kotlinSolutionSuite = Chapter6Problem5()
    private val javaSolutionSuite = JavaChapter6Problem5()

    private val shortInput = intArrayOf(
        2148,
        9058,
        7742,
        3153,
        6324,
        609,
        7628,
        5469,
        7017,
        504,
    )
        .map { it.toLong() }
        .toTypedArray()
    private val longInput = intArrayOf(
        2148,
        9058,
        7742,
        3153,
        6324,
        609,
        7628,
        5469,
        7017,
        504,
        4092,
        1582,
        9572,
        1542,
        5697,
        2081,
        4218,
        3130,
        7923,
        9595,
        6558,
        3859,
        9832,
        3062,
        6788,
        7578,
        7432,
        479,
        8439,
        9079,
        7173,
        2667,
        2770,
        2655,
        972,
        4264,
        2014,
        3171,
        4715,
        345,
        4388,
        3816,
        8887,
        3915,
        3490,
        2327,
        123,
        4596,
        4307,
        8737,
        4007,
        6798,
        6551,
        1627,
        1190,
        4984,
        2480,
        3404,
        2027,
        4778,
        2951,
        2795,
        5002,
        8121,
        8910,
        9593,
        5254,
        448,
        6237,
        5565,
        1816,
        392,
        8143,
        9310,
        9293,
        3138,
        4869,
        6756,
        872,
        6183,
        3517,
        3513,
        1676,
        5498,
        9172,
        5739,
        6108,
        7538,
        7671,
        5780,
        8666,
        540,
        9771,
        6837,
        9341,
        1590,
        5689,
        1605,
        1103,
        5859,
    )
        .map { it.toLong() }
        .toTypedArray()
    private val piDecimals = Helper.readDecimal("problem6.5_pi1000000.txt")
        .drop(2)
        .chunked(10)
        .map { it.toLong() }
        .toTypedArray()

    @Test
    fun authorTestCase1() {
        // given
        val kotlinRSelectInput = shortInput.copyOf()
        val kotlinDSelectInput = shortInput.copyOf()
        val javaRSelectInput = shortInput.copyOf().toLongArray()
        val javaDSelectInput = shortInput.copyOf().toLongArray()

        // when
        val kotlinRSelectActual = kotlinSolutionSuite.rSelect(
            input = kotlinRSelectInput,
            i = 5,
            leftEndpoint = 0,
            rightEndpoint = kotlinRSelectInput.lastIndex,
        )
        val kotlinDSelectActual = kotlinSolutionSuite.dSelect(
            input = kotlinDSelectInput,
            i = 5,
        )
        val javaRSelectActual = javaSolutionSuite.rSelect(
            javaRSelectInput,
            5,
            0,
            javaRSelectInput.lastIndex,
        )
        val javaDSelectActual = javaSolutionSuite.dSelect(
            javaDSelectInput,
            5,
        )

        // then
        assertAll(
            { assertEquals(5469, kotlinRSelectActual) },
            { assertEquals(5469, kotlinDSelectActual) },
            { assertEquals(5469, javaRSelectActual) },
            { assertEquals(5469, javaDSelectActual) },
        )
    }

    @Test
    fun authorTestCase2() {
        // given
        val kotlinRSelectInput = longInput.copyOf()
        val kotlinDSelectInput = longInput.copyOf()
        val javaRSelectInput = longInput.copyOf().toLongArray()
        val javaDSelectInput = longInput.copyOf().toLongArray()

        // when
        val kotlinRSelectActual = kotlinSolutionSuite.rSelect(
            input = kotlinRSelectInput,
            i = 50,
            leftEndpoint = 0,
            rightEndpoint = kotlinRSelectInput.lastIndex,
        )
        val kotlinDSelectActual = kotlinSolutionSuite.dSelect(
            input = kotlinDSelectInput,
            i = 50,
        )
        val javaRSelectActual = javaSolutionSuite.rSelect(
            javaRSelectInput,
            50,
            0,
            javaRSelectInput.lastIndex,
        )
        val javaDSelectActual = javaSolutionSuite.dSelect(
            javaDSelectInput,
            50,
        )

        // then
        assertAll(
            { assertEquals(4715, kotlinRSelectActual) },
            { assertEquals(4715, kotlinDSelectActual) },
            { assertEquals(4715, javaRSelectActual) },
            { assertEquals(4715, javaDSelectActual) },
        )
    }

    @Test
    fun authorTestCase3() {
        // given
        val kotlinRSelectInput = piDecimals.copyOf()
        val kotlinDSelectInput = piDecimals.copyOf()
        val javaRSelectInput = piDecimals.copyOf().toLongArray()
        val javaDSelectInput = piDecimals.copyOf().toLongArray()

        // when
        val kotlinRSelectActual = kotlinSolutionSuite.rSelect(
            input = kotlinRSelectInput,
            i = kotlinRSelectInput.size / 2,
            leftEndpoint = 0,
            rightEndpoint = kotlinRSelectInput.lastIndex,
        )
        val kotlinDSelectActual = kotlinSolutionSuite.dSelect(
            input = kotlinDSelectInput,
            i = kotlinRSelectInput.size / 2,
        )
        val javaRSelectActual = javaSolutionSuite.rSelect(
            javaRSelectInput,
            javaRSelectInput.size / 2,
            0,
            javaRSelectInput.lastIndex,
        )
        val javaDSelectActual = javaSolutionSuite.dSelect(
            javaDSelectInput,
            javaRSelectInput.size / 2,
        )

        val duplicated = mutableSetOf<Long>().let { mutableSet ->
            kotlinRSelectInput.any {
                !mutableSet.add(it)
            }
        }

        assertAll(
            { assertTrue(duplicated) },
            { assertEquals(4972700282, kotlinRSelectActual) },
            { assertEquals(4972700282, kotlinDSelectActual) },
            { assertEquals(4972700282, javaRSelectActual) },
            { assertEquals(4972700282, javaDSelectActual) },
        )
    }
}
