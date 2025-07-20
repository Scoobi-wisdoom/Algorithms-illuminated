import Chapter11Problem3.CustomAVLTree
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.system.measureTimeMillis
import kotlin.test.assertTrue

class Chapter11Problem3Test {
    private val kotlinSolutionSuite = Chapter11Problem3()
    private val javanSolutionSuite = JavaChapter11Problem3()

    @Nested
    inner class CustomAVLTreeTest {
        @Test
        fun `insert operation maintains balance`() {
            // given/when
            val kotlinCustomAVLTree = CustomAVLTree(1)
                .insert(2)
                .insert(3)
                .insert(4)
                .insert(5)
                .insert(6)
                .insert(7)
            val javaCustomAVLTree = JavaChapter11Problem3.CustomAVLTree(1)
                .insert(2)
                .insert(3)
                .insert(4)
                .insert(5)
                .insert(6)
                .insert(7)

            // then
            /**
             *           3
             *          / \
             *         2   5
             *        /   / \
             *       1   4   6
             *                \
             *                 7
             */
            assertAll(
                { assertEquals(3, kotlinCustomAVLTree.key) },
                { assertEquals(3, kotlinCustomAVLTree.height) },

                { assertEquals(2, kotlinCustomAVLTree.left!!.key) },
                { assertEquals(5, kotlinCustomAVLTree.right!!.key) },
                { assertEquals(1, kotlinCustomAVLTree.left!!.height) },
                { assertEquals(2, kotlinCustomAVLTree.right!!.height) },

                { assertEquals(1, kotlinCustomAVLTree.left!!.left!!.key) },
                { assertEquals(4, kotlinCustomAVLTree.right!!.left!!.key) },
                { assertEquals(6, kotlinCustomAVLTree.right!!.right!!.key) },
                { assertEquals(0, kotlinCustomAVLTree.left!!.left!!.height) },
                { assertEquals(0, kotlinCustomAVLTree.right!!.left!!.height) },
                { assertEquals(1, kotlinCustomAVLTree.right!!.right!!.height) },

                { assertEquals(7, kotlinCustomAVLTree.right!!.right!!.right!!.key) },
                { assertEquals(0, kotlinCustomAVLTree.right!!.right!!.right!!.height) },


                { assertEquals(3, javaCustomAVLTree.key) },
                { assertEquals(3, javaCustomAVLTree.height) },

                { assertEquals(2, javaCustomAVLTree.left!!.key) },
                { assertEquals(5, javaCustomAVLTree.right!!.key) },
                { assertEquals(1, javaCustomAVLTree.left!!.height) },
                { assertEquals(2, javaCustomAVLTree.right!!.height) },

                { assertEquals(1, javaCustomAVLTree.left!!.left!!.key) },
                { assertEquals(4, javaCustomAVLTree.right!!.left!!.key) },
                { assertEquals(6, javaCustomAVLTree.right!!.right!!.key) },
                { assertEquals(0, javaCustomAVLTree.left!!.left!!.height) },
                { assertEquals(0, javaCustomAVLTree.right!!.left!!.height) },
                { assertEquals(1, javaCustomAVLTree.right!!.right!!.height) },

                { assertEquals(7, javaCustomAVLTree.right!!.right!!.right!!.key) },
                { assertEquals(0, javaCustomAVLTree.right!!.right!!.right!!.height) },
            )
        }

        @Test
        fun `search tree selects an element at the ith position`() {
            // given
            val input = intArrayOf(
                6331,
                2793,
                1640,
                9290,
                225,
                625,
                6195,
            )
            val kotlinCustomAVLTree = with(CustomAVLTree(input.first())) {
                input.drop(1).fold(this) { tree, key -> tree.insert(key) }
            }
            val javaCustomAVLTree = with(JavaChapter11Problem3.CustomAVLTree(input.first())) {
                input.drop(1).fold(this) { tree, key -> tree.insert(key) }
            }

            assertAll(
                { assertEquals(225, kotlinCustomAVLTree.select(1)) },
                { assertEquals(625, kotlinCustomAVLTree.select(2)) },
                { assertEquals(1640, kotlinCustomAVLTree.select(3)) },
                { assertEquals(2793, kotlinCustomAVLTree.select(4)) },
                { assertEquals(6195, kotlinCustomAVLTree.select(5)) },
                { assertEquals(6331, kotlinCustomAVLTree.select(6)) },
                { assertEquals(9290, kotlinCustomAVLTree.select(7)) },

                { assertThrows<IllegalArgumentException> { kotlinCustomAVLTree.select(0) } },
                { assertThrows<IllegalArgumentException> { kotlinCustomAVLTree.select(8) } },


                { assertEquals(225, javaCustomAVLTree.select(1)) },
                { assertEquals(625, javaCustomAVLTree.select(2)) },
                { assertEquals(1640, javaCustomAVLTree.select(3)) },
                { assertEquals(2793, javaCustomAVLTree.select(4)) },
                { assertEquals(6195, javaCustomAVLTree.select(5)) },
                { assertEquals(6331, javaCustomAVLTree.select(6)) },
                { assertEquals(9290, javaCustomAVLTree.select(7)) },

                { assertThrows<IllegalArgumentException> { javaCustomAVLTree.select(0) } },
                { assertThrows<IllegalArgumentException> { javaCustomAVLTree.select(8) } },
            )
        }
    }

    @Nested
    inner class KthMedianTest {
        @Test
        fun authorTestCase1() {
            // given
            val input = intArrayOf(
                6331,
                2793,
                1640,
                9290,
                225,
                625,
                6195,
                2303,
                5685,
                1354,
            )

            // when
            val kotlinHeapKthMedians = kotlinSolutionSuite.getHeapKthMedians(input)
            val kotlinSearchTreeKthMedians = kotlinSolutionSuite.getSearchTreeKthMedians(input)
            val javaHeapKthMedians = javanSolutionSuite.getHeapKthMedians(input)
            val javaSearchTreeKthMedians = javanSolutionSuite.getSearchTreeKthMedians(input)

            // then
            assertAll(
                { assertEquals(9335, kotlinHeapKthMedians.sum() % 10_000) },
                { assertEquals(9335, kotlinSearchTreeKthMedians.sum() % 10_000) },

                { assertEquals(9335, javaHeapKthMedians.sum() % 10_000) },
                { assertEquals(9335, javaSearchTreeKthMedians.sum() % 10_000) },
            )
        }

        @Test
        fun authorTestCase2() {
            // given
            val input = Helper.readIntArray("problem11.3.txt")

            // when
            val kotlinHeapKthMedians = kotlinSolutionSuite.getHeapKthMedians(input)
            val kotlinSearchTreeKthMedians = kotlinSolutionSuite.getSearchTreeKthMedians(input)
            val javaHeapKthMedians = javanSolutionSuite.getHeapKthMedians(input)
            val javaSearchTreeKthMedians = javanSolutionSuite.getSearchTreeKthMedians(input)

            // then
            assertAll(
                { assertEquals(1213, kotlinHeapKthMedians.sum() % 10_000) },
                { assertEquals(1213, kotlinSearchTreeKthMedians.sum() % 10_000) },

                { assertEquals(1213, javaHeapKthMedians.sum() % 10_000) },
                { assertEquals(1213, javaSearchTreeKthMedians.sum() % 10_000) },
            )
        }
    }

    @Test
    fun `Compare kth mean solutions between heap's vs balanced tree's`() {
        // given
        val input = Helper.readIntArray("problem11.3.txt")

        // when
        val kotlinHeapTimeActual = measureTimeMillis { kotlinSolutionSuite.getHeapKthMedians(input) }
        val kotlinSearchTreeTimeActual = measureTimeMillis { kotlinSolutionSuite.getSearchTreeKthMedians(input) }
        val javaHeapTimeActual = measureTimeMillis { javanSolutionSuite.getHeapKthMedians(input) }
        val javaSearchTreeTimeActual = measureTimeMillis { javanSolutionSuite.getSearchTreeKthMedians(input) }

        // then
        assertAll(
            { assertTrue(kotlinHeapTimeActual < kotlinSearchTreeTimeActual) },
            { assertTrue(javaHeapTimeActual < javaSearchTreeTimeActual) },
        )
    }
}
