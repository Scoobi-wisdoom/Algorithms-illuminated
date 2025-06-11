import Chapter8Problem10Test.Constants.FIVE
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Chapter8Problem10Test {
    private val kotlinSolutionSuite = Chapter8Problem10()
    private val javaSolutionSuite = JavaChapter8Problem10()

    @Nested
    inner class AuthorTest {
        @Test
        fun authorTestCase1() {
            // given
            val adjacencyList = """
                1 4
                2 8
                3 6
                4 7
                5 2
                6 9
                7 1
                8 5
                8 6
                9 7
                9 3
        """.trimIndent()

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            // then
            assertAll(
                { assertArrayEquals(intArrayOf(3, 3, 3, 0, 0), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(3, 3, 3, 0, 0), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        @Test
        fun authorTestCase2() {
            // given
            val adjacencyList = """
                1 2
                2 6
                2 3
                2 4
                3 1
                3 4
                4 5
                5 4
                6 5
                6 7
                7 6
                7 8
                8 5
                8 7
        """.trimIndent()

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            // then
            assertAll(
                { assertArrayEquals(intArrayOf(3, 3, 2, 0, 0), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(3, 3, 2, 0, 0), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        @Test
        fun authorTestCase3() {
            // given
            val adjacencyList = """
                1 2
                2 3
                3 1
                3 4
                5 4
                6 4
                8 6
                6 7
                7 8
        """.trimIndent()

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            // then
            assertAll(
                { assertArrayEquals(intArrayOf(3, 3, 1, 1, 0), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(3, 3, 1, 1, 0), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        @Test
        fun authorTestCase4() {
            // given
            val adjacencyList = """
                1 2
                2 3
                3 1
                3 4
                5 4
                6 4
                8 6
                6 7
                7 8
                4 3
                4 6
        """.trimIndent()

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            // then
            assertAll(
                { assertArrayEquals(intArrayOf(7, 1, 0, 0, 0), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(7, 1, 0, 0, 0), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        @Test
        fun authorTestCase5() {
            // given
            val adjacencyList = """
                1 2
                2 3
                2 4
                2 5
                3 6
                4 5
                4 7
                5 2
                5 6
                5 7
                6 3
                6 8
                7 8
                7 10
                8 7
                9 7
                10 9
                10 11
                11 12
                12 10
        """.trimIndent()

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            // then
            assertAll(
                { assertArrayEquals(intArrayOf(6, 3, 2, 1, 0), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(6, 3, 2, 1, 0), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        @Test
        fun authorChallengeCase() {
            // given
            val adjacencyList = Helper.readEdge("problem8.10.txt")

            // when
            val kotlinKosarajuActual = kotlinSolutionSuite.getKosarajuSccNumbers(adjacencyList)
            val javaKosarajuActual = javaSolutionSuite.getKosarajuSccNumbers(adjacencyList)

            assertAll(
                { assertArrayEquals(intArrayOf(434_821, 968, 459, 313, 211), getTopFiveSizes(kotlinKosarajuActual)) },
                { assertArrayEquals(intArrayOf(434_821, 968, 459, 313, 211), getTopFiveSizes(javaKosarajuActual)) },
            )
        }

        private fun getTopFiveSizes(kosarajuSccNumbers: IntArray): IntArray {
            return kosarajuSccNumbers.groupBy { it }
                .map { (_, duplicates) ->
                    duplicates.size
                }
                .sortedDescending()
                .take(FIVE).let {
                    it + List(FIVE - it.size) { 0 }
                }.toIntArray()
        }
    }

    @Nested
    inner class CustomTest {
        @Test
        fun getTopologicalSortFValues() {
            val edge = mapOf(
                Pair(1, setOf(2, 3)),
                Pair(2, setOf(4)),
                Pair(3, setOf(4)),
            )

            val kotlinActual = kotlinSolutionSuite.getTopologicalSortFValues(edge)
            val javaActual = javaSolutionSuite.getTopologicalSortFValues(edge).toIntArray()

            assertAll(
                { assertArrayEquals(intArrayOf(1, 3, 2, 4), kotlinActual) },
                { assertArrayEquals(intArrayOf(1, 3, 2, 4), javaActual) },
            )
        }
    }

    private object Constants {
        const val FIVE = 5
    }
}
