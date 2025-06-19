import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class Chapter9Problem8Test {
    private val kotlinSolutionSuite = Chapter9Problem8()
    private val javaSolutionSuite = JavaChapter9Problem8()

    @Test
    fun authorTestCase1() {
        val inputGraph = """
            1	2,1	8,2
            2	1,1	3,1
            3	2,1	4,1
            4	3,1	5,1
            5	4,1	6,1
            6	5,1	7,1
            7	6,1	8,1
            8	7,1	1,2
        """.trimIndent()

        val kotlinDijkstraActual = kotlinSolutionSuite.getDijkstraLengths(inputGraph)
        val javaDijkstraActual = javaSolutionSuite.getDijkstraLengths(inputGraph)

        assertAll(
            { assertArrayEquals(intArrayOf(0, 1, 2, 3, 4, 4, 3, 2), kotlinDijkstraActual) },
            { assertArrayEquals(intArrayOf(0, 1, 2, 3, 4, 4, 3, 2), javaDijkstraActual) },
        )
    }

    @Test
    fun authorTestCase2() {
        val inputGraph: String = Helper.readEdge("problem9.8.txt")

        val kotlinDijkstraActual = kotlinSolutionSuite.getDijkstraLengths(inputGraph)
        val javaDijkstraActual = javaSolutionSuite.getDijkstraLengths(inputGraph)

        assertAll(
            { assertEquals(2599, kotlinDijkstraActual[6]) },
            { assertEquals(2610, kotlinDijkstraActual[36]) },
            { assertEquals(2947, kotlinDijkstraActual[58]) },
            { assertEquals(2052, kotlinDijkstraActual[81]) },
            { assertEquals(2367, kotlinDijkstraActual[98]) },
            { assertEquals(2399, kotlinDijkstraActual[114]) },
            { assertEquals(2029, kotlinDijkstraActual[132]) },
            { assertEquals(2442, kotlinDijkstraActual[164]) },
            { assertEquals(2505, kotlinDijkstraActual[187]) },
            { assertEquals(3068, kotlinDijkstraActual[196]) },

            { assertEquals(2599, javaDijkstraActual[6]) },
            { assertEquals(2610, javaDijkstraActual[36]) },
            { assertEquals(2947, javaDijkstraActual[58]) },
            { assertEquals(2052, javaDijkstraActual[81]) },
            { assertEquals(2367, javaDijkstraActual[98]) },
            { assertEquals(2399, javaDijkstraActual[114]) },
            { assertEquals(2029, javaDijkstraActual[132]) },
            { assertEquals(2442, javaDijkstraActual[164]) },
            { assertEquals(2505, javaDijkstraActual[187]) },
            { assertEquals(3068, javaDijkstraActual[196]) },
        )
    }
}
