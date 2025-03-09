import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class Chapter5Problem6 {
    enum class PivotStrategy {
        FIRST,
        LAST,
        RANDOM,
        MEDIAN_OF_THREE,
        ;

        fun choosePivot(
            input: IntArray,
            leftEndpoint: Int,
            rightEndpoint: Int,
        ): Int {
            require(leftEndpoint >= 0 && rightEndpoint >= 0) { "Positive endpoints are expected." }
            require(leftEndpoint <= rightEndpoint) { "Pivot choice on empty options." }
            return when (this) {
                FIRST -> leftEndpoint
                LAST -> rightEndpoint
                RANDOM -> Random.nextInt(from = leftEndpoint, until = rightEndpoint + 1)
                MEDIAN_OF_THREE -> {
                    val middleIndex = (leftEndpoint + rightEndpoint) / 2
                    val first = input[leftEndpoint]
                    val middle = input[middleIndex]
                    val last = input[rightEndpoint]

                    when {
                        (first > min(middle, last)) && (first < max(middle, last)) -> leftEndpoint
                        (middle > min(first, last)) && (middle < max(first, last)) -> middleIndex
                        else -> rightEndpoint
                    }
                }
            }
        }
    }

    fun quickSort(
        pivotStrategy: PivotStrategy,
        input: IntArray,
        leftEndpoint: Int,
        rightEndpoint: Int,
    ): Long {
        return if (leftEndpoint >= rightEndpoint) 0 else {
            pivotStrategy.choosePivot(
                input = input,
                leftEndpoint = leftEndpoint,
                rightEndpoint = rightEndpoint,
            ).let {
                val originalLeftEndpointValue = input[leftEndpoint]
                input[leftEndpoint] = input[it]
                input[it] = originalLeftEndpointValue
            }

            val pivotIndex = executePartition(
                input = input,
                pivotIndex = leftEndpoint,
                rightEndpoint = rightEndpoint,
            )

            rightEndpoint - leftEndpoint +
                    quickSort(
                        pivotStrategy = pivotStrategy,
                        input = input,
                        leftEndpoint = leftEndpoint,
                        rightEndpoint = pivotIndex - 1,
                    ) +
                    quickSort(
                        pivotStrategy = pivotStrategy,
                        input = input,
                        leftEndpoint = pivotIndex + 1,
                        rightEndpoint = rightEndpoint,
                    )
        }
    }

    private fun executePartition(
        input: IntArray,
        pivotIndex: Int,
        rightEndpoint: Int,
    ): Int {
        var i = pivotIndex + 1
        for (j in (pivotIndex + 1)..rightEndpoint) {
            if (input[j] < input[pivotIndex]) {
                val iValue = input[i]
                input[i] = input[j]
                input[j] = iValue
                i++
            }
        }
        val pivot = input[pivotIndex]
        input[pivotIndex] = input[i - 1]
        input[i - 1] = pivot
        return i - 1
    }
}
