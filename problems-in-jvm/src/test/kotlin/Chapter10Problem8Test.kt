import JavaChapter10Problem8.CustomHeapElement
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Chapter10Problem8Test {
    private val kotlinSolutionSuite = Chapter10Problem8()
    private val javaSolutionSuite = JavaChapter10Problem8()

    @Nested
    inner class CustomHeapTest {
        @Test
        fun `First extractMin operation returns the starting vertex`() {
            // given
            val kotlinCustomHeap = Chapter10Problem8.CustomHeap(vertices = listOf(1, 2, 3))
            val javaCustomHeap = JavaChapter10Problem8.CustomHeap(listOf(1, 2, 3).toIntArray())

            // when/then
            assertAll(
                { assertEquals(Pair(1, 0), kotlinCustomHeap.extractMin()) },
                { assertEquals(CustomHeapElement(1, 0), javaCustomHeap.extractMin()) },
            )
        }

        @Test
        fun `Insert sorts the heap according to the key`() {
            // given
            val kotlinCustomHeap = Chapter10Problem8.CustomHeap(vertices = listOf(1))
            val javaCustomHeap = JavaChapter10Problem8.CustomHeap(listOf(1).toIntArray())

            // when
            kotlinCustomHeap.insert(Pair(4, 4))
            kotlinCustomHeap.insert(Pair(2, 2))
            kotlinCustomHeap.insert(Pair(3, 3))
            javaCustomHeap.insert(CustomHeapElement(4, 4))
            javaCustomHeap.insert(CustomHeapElement(2, 2))
            javaCustomHeap.insert(CustomHeapElement(3, 3))

            // then
            assertAll(
                { assertEquals(4, kotlinCustomHeap.heapArray.size) },
                { assertEquals(Pair(1, 0), kotlinCustomHeap.heapArray[0]) },
                { assertEquals(Pair(3, 3), kotlinCustomHeap.heapArray[1]) },
                { assertEquals(Pair(2, 2), kotlinCustomHeap.heapArray[2]) },
                { assertEquals(Pair(4, 4), kotlinCustomHeap.heapArray[3]) },

                { assertEquals(4, javaCustomHeap.heapArray.size) },
                { assertEquals(CustomHeapElement(1, 0), javaCustomHeap.heapArray[0]) },
                { assertEquals(CustomHeapElement(3, 3), javaCustomHeap.heapArray[1]) },
                { assertEquals(CustomHeapElement(2, 2), javaCustomHeap.heapArray[2]) },
                { assertEquals(CustomHeapElement(4, 4), javaCustomHeap.heapArray[3]) },
            )
        }

        @Test
        fun `ExtractMin sorts the heap according to the key`() {
            // given
            val kotlinCustomHeap = Chapter10Problem8.CustomHeap(vertices = listOf(1))
            kotlinCustomHeap.insert(Pair(4, 4))
            kotlinCustomHeap.insert(Pair(2, 2))
            kotlinCustomHeap.insert(Pair(3, 3))

            val javaCustomHeap = JavaChapter10Problem8.CustomHeap(listOf(1).toIntArray())
            javaCustomHeap.insert(CustomHeapElement(4, 4))
            javaCustomHeap.insert(CustomHeapElement(2, 2))
            javaCustomHeap.insert(CustomHeapElement(3, 3))

            // when
            kotlinCustomHeap.extractMin()
            javaCustomHeap.extractMin()

            // then
            assertAll(
                { assertEquals(3, kotlinCustomHeap.heapArray.size) },
                { assertEquals(Pair(2, 2), kotlinCustomHeap.heapArray[0]) },
                { assertEquals(Pair(3, 3), kotlinCustomHeap.heapArray[1]) },
                { assertEquals(Pair(4, 4), kotlinCustomHeap.heapArray[2]) },

                { assertEquals(3, javaCustomHeap.heapArray.size) },
                { assertEquals(CustomHeapElement(2, 2), javaCustomHeap.heapArray[0]) },
                { assertEquals(CustomHeapElement(3, 3), javaCustomHeap.heapArray[1]) },
                { assertEquals(CustomHeapElement(4, 4), javaCustomHeap.heapArray[2]) },
            )
        }

        @Test
        fun `Remove sorts the heap according to the key`() {
            // given
            val kotlinCustomHeap = Chapter10Problem8.CustomHeap(vertices = listOf(1))
            kotlinCustomHeap.insert(Pair(4, 4))
            kotlinCustomHeap.insert(Pair(2, 2))
            kotlinCustomHeap.insert(Pair(3, 3))

            val javaCustomHeap = JavaChapter10Problem8.CustomHeap(listOf(1).toIntArray())
            javaCustomHeap.insert(CustomHeapElement(4, 4))
            javaCustomHeap.insert(CustomHeapElement(2, 2))
            javaCustomHeap.insert(CustomHeapElement(3, 3))

            // when
            val kotlinVertex3 = kotlinCustomHeap.remove(3)
            val javaVertex3 = javaCustomHeap.remove(3)

            // when
            assertAll(
                { assertEquals(Pair(3, 3), kotlinVertex3) },
                { assertEquals(3, kotlinCustomHeap.heapArray.size) },
                { assertEquals(Pair(1, 0), kotlinCustomHeap.heapArray[0]) },
                { assertEquals(Pair(4, 4), kotlinCustomHeap.heapArray[1]) },
                { assertEquals(Pair(2, 2), kotlinCustomHeap.heapArray[2]) },

                { assertEquals(CustomHeapElement(3, 3), javaVertex3) },
                { assertEquals(3, javaCustomHeap.heapArray.size) },
                { assertEquals(CustomHeapElement(1, 0), javaCustomHeap.heapArray[0]) },
                { assertEquals(CustomHeapElement(4, 4), javaCustomHeap.heapArray[1]) },
                { assertEquals(CustomHeapElement(2, 2), javaCustomHeap.heapArray[2]) },
            )
        }
    }

    @Nested
    inner class DijkstraTest {
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

    @Test
    fun `Compare Dijkstra algorithms between without a heap and with a heap`() {
        // given
        val inputGraph: String = Helper.readEdge("problem9.8.txt")
        val kotlinWithoutHeapSolutionSuite = Chapter9Problem8()
        val javaWithoutHeapSolutionSuite = JavaChapter9Problem8()

        // when
        val kotlinDijkstraHeapActual = measureTimeMillis { kotlinSolutionSuite.getDijkstraLengths(inputGraph) }
        val javaDijkstraHeapActual = measureTimeMillis { javaSolutionSuite.getDijkstraLengths(inputGraph) }
        val kotlinDijkstraWithoutHeapActual =
            measureTimeMillis { kotlinWithoutHeapSolutionSuite.getDijkstraLengths(inputGraph) }
        val javaDijkstraWithoutHeapActual =
            measureTimeMillis { javaWithoutHeapSolutionSuite.getDijkstraLengths(inputGraph) }

        // then
        assertAll(
            { assertTrue { kotlinDijkstraHeapActual < kotlinDijkstraWithoutHeapActual } },
            { assertTrue { javaDijkstraHeapActual < javaDijkstraWithoutHeapActual } },
        )
    }
}
