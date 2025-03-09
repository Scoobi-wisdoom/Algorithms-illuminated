import Chapter5Problem6.PivotStrategy.FIRST
import Chapter5Problem6.PivotStrategy.LAST
import Chapter5Problem6.PivotStrategy.MEDIAN_OF_THREE
import Chapter5Problem6.PivotStrategy.RANDOM
import JavaChapter5Problem6.PivotStrategy
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Chapter5Problem6Test {
    private val kotlinSolutionSuite = Chapter5Problem6()
    private val javaSolutionSuite = JavaChapter5Problem6()

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
    private val longerInput = Helper.readIntArray("problem5.6.txt")

    @Nested
    inner class CompareNonRandom {
        @Test
        fun firstElementPivot() {
            // given
            val kotlinShortInputCopy = shortInput.copyOf()
            val kotlinLongInputCopy = longInput.copyOf()
            val kotlinLongerInputCopy = longerInput.copyOf()

            val javaShortInputCopy = shortInput.copyOf()
            val javaLongInputCopy = longInput.copyOf()
            val javaLongerInputCopy = longerInput.copyOf()

            // when
            val kotlinShortComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = FIRST,
                input = kotlinShortInputCopy,
                leftEndpoint = 0,
                rightEndpoint = shortInput.lastIndex,
            )
            val kotlinLongComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = FIRST,
                input = kotlinLongInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longInput.lastIndex,
            )
            val kotlinLongerComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = FIRST,
                input = kotlinLongerInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )

            val javaShortComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.FIRST,
                javaShortInputCopy,
                0,
                shortInput.lastIndex,
            )
            val javaLongComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.FIRST,
                javaLongInputCopy,
                0,
                longInput.lastIndex,
            )
            val javaLongerComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.FIRST,
                javaLongerInputCopy,
                0,
                longerInput.lastIndex,
            )

            // then
            assertAll(
                { assertTrue(kotlinShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(25, kotlinShortComparisonCount) },
                { assertTrue(kotlinLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(620, kotlinLongComparisonCount) },
                { assertTrue(kotlinLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(162085, kotlinLongerComparisonCount) },

                { assertTrue(javaShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(25, javaShortComparisonCount) },
                { assertTrue(javaLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(620, javaLongComparisonCount) },
                { assertTrue(javaLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(162085, javaLongerComparisonCount) },
            )
        }

        @Test
        fun lastElementPivot() {
            // given
            val kotlinShortInputCopy = shortInput.copyOf()
            val kotlinLongInputCopy = longInput.copyOf()
            val kotlinLongerInputCopy = longerInput.copyOf()

            val javaShortInputCopy = shortInput.copyOf()
            val javaLongInputCopy = longInput.copyOf()
            val javaLongerInputCopy = longerInput.copyOf()

            // when
            val kotlinShortComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = LAST,
                input = kotlinShortInputCopy,
                leftEndpoint = 0,
                rightEndpoint = shortInput.lastIndex,
            )
            val kotlinLongComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = LAST,
                input = kotlinLongInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longInput.lastIndex,
            )
            val kotlinLongerComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = LAST,
                input = kotlinLongerInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )

            val javaShortComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.LAST,
                javaShortInputCopy,
                0,
                shortInput.lastIndex,
            )
            val javaLongComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.LAST,
                javaLongInputCopy,
                0,
                longInput.lastIndex,
            )
            val javaLongerComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.LAST,
                javaLongerInputCopy,
                0,
                longerInput.lastIndex,
            )

            // then
            assertAll(
                { assertTrue(kotlinShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(31, kotlinShortComparisonCount) },
                { assertTrue(kotlinLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(573, kotlinLongComparisonCount) },
                { assertTrue(kotlinLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(164123, kotlinLongerComparisonCount) },

                { assertTrue(javaShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(31, javaShortComparisonCount) },
                { assertTrue(javaLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(573, javaLongComparisonCount) },
                { assertTrue(javaLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(164123, javaLongerComparisonCount) },
            )
        }

        @Test
        fun medianOfThreePivot() {
            // given
            val kotlinShortInputCopy = shortInput.copyOf()
            val kotlinLongInputCopy = longInput.copyOf()
            val kotlinLongerInputCopy = longerInput.copyOf()

            val javaShortInputCopy = shortInput.copyOf()
            val javaLongInputCopy = longInput.copyOf()
            val javaLongerInputCopy = longerInput.copyOf()

            // when
            val kotlinShortComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = MEDIAN_OF_THREE,
                input = kotlinShortInputCopy,
                leftEndpoint = 0,
                rightEndpoint = shortInput.lastIndex,
            )
            val kotlinLongComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = MEDIAN_OF_THREE,
                input = kotlinLongInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longInput.lastIndex,
            )
            val kotlinLongerComparisonCount = kotlinSolutionSuite.quickSort(
                pivotStrategy = MEDIAN_OF_THREE,
                input = kotlinLongerInputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )

            val javaShortComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.MEDIAN_OF_THREE,
                javaShortInputCopy,
                0,
                shortInput.lastIndex,
            )
            val javaLongComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.MEDIAN_OF_THREE,
                javaLongInputCopy,
                0,
                longInput.lastIndex,
            )
            val javaLongerComparisonCount = javaSolutionSuite.quickSort(
                PivotStrategy.MEDIAN_OF_THREE,
                javaLongerInputCopy,
                0,
                longerInput.lastIndex,
            )

            // then
            assertAll(
                { assertTrue(kotlinShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(21, kotlinShortComparisonCount) },
                { assertTrue(kotlinLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(502, kotlinLongComparisonCount) },
                { assertTrue(kotlinLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(138382, kotlinLongerComparisonCount) },

                { assertTrue(javaShortInputCopy.contentEquals(shortInput.sorted().toIntArray())) },
                { assertEquals(21, javaShortComparisonCount) },
                { assertTrue(javaLongInputCopy.contentEquals(longInput.sorted().toIntArray())) },
                { assertEquals(502, javaLongComparisonCount) },
                { assertTrue(javaLongerInputCopy.contentEquals(longerInput.sorted().toIntArray())) },
                { assertEquals(138382, javaLongerComparisonCount) },
            )
        }
    }

    @Nested
    inner class CompareRandomToNonRandom {
        @Test
        fun sort() {
            // given
            val inputCopy = longerInput.copyOf()

            // when
            kotlinSolutionSuite.quickSort(
                pivotStrategy = RANDOM,
                input = inputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )

            assertTrue(inputCopy.contentEquals(longerInput.sorted().toIntArray()))
        }

        /**
         * This test may fail since it depends on randomness.
         */
        @Test
        fun averageRandomComparisonCount() {
            // given
            val inputCopy = longerInput.copyOf()
            val kotlinFirstPivotResult = kotlinSolutionSuite.quickSort(
                pivotStrategy = FIRST,
                input = inputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )
            val kotlinLastPivotResult = kotlinSolutionSuite.quickSort(
                pivotStrategy = LAST,
                input = inputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )
            val kotlinMedianOfThreeResult = kotlinSolutionSuite.quickSort(
                pivotStrategy = MEDIAN_OF_THREE,
                input = inputCopy,
                leftEndpoint = 0,
                rightEndpoint = longerInput.lastIndex,
            )

            val javaFirstPivotResult = javaSolutionSuite.quickSort(
                PivotStrategy.FIRST,
                inputCopy,
                0,
                longerInput.lastIndex,
            )
            val javaLastPivotResult = javaSolutionSuite.quickSort(
                PivotStrategy.LAST,
                inputCopy,
                0,
                longerInput.lastIndex,
            )
            val javaMedianOfThreeResult = javaSolutionSuite.quickSort(
                PivotStrategy.MEDIAN_OF_THREE,
                inputCopy,
                0,
                longerInput.lastIndex,
            )

            // when
            val kotlinAverageComparisonCount = List(10) {
                kotlinSolutionSuite.quickSort(
                    pivotStrategy = RANDOM,
                    input = longerInput.copyOf(),
                    leftEndpoint = 0,
                    rightEndpoint = longerInput.lastIndex,
                )
            }.average()

            val javaAverageComparisonCount = List(10) {
                javaSolutionSuite.quickSort(
                    PivotStrategy.RANDOM,
                    longerInput.copyOf(),
                    0,
                    longerInput.lastIndex,
                )
            }.average()

            assertAll(
                { assertTrue(kotlinAverageComparisonCount < kotlinFirstPivotResult) },
                { assertTrue(kotlinAverageComparisonCount < kotlinLastPivotResult) },
                { assertTrue(kotlinAverageComparisonCount > kotlinMedianOfThreeResult) },

                { assertTrue(javaAverageComparisonCount < javaFirstPivotResult) },
                { assertTrue(javaAverageComparisonCount < javaLastPivotResult) },
                { assertTrue(javaAverageComparisonCount > javaMedianOfThreeResult) },
            )
        }
    }
}
