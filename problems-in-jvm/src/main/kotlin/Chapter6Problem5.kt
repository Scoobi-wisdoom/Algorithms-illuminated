import kotlin.random.Random

class Chapter6Problem5 {
    fun rSelect(
        input: Array<Long>,
        i: Int,
        leftEndpoint: Int,
        rightEndpoint: Int,
    ): Long {
        return if (leftEndpoint >= rightEndpoint) {
            input[leftEndpoint]
        } else {
            Random.nextInt(from = leftEndpoint, until = rightEndpoint + 1).let {
                val leftEndpointValue = input[leftEndpoint]
                input[leftEndpoint] = input[it]
                input[it] = leftEndpointValue
            }
            val pivotIndex = executePartition(
                input = input,
                pivotIndex = leftEndpoint,
                rightEndpoint = rightEndpoint,
            )

            if (pivotIndex + 1 == i) {
                input[pivotIndex]
            } else if (pivotIndex + 1 > i) {
                rSelect(
                    input = input,
                    i = i,
                    leftEndpoint = 0,
                    rightEndpoint = pivotIndex - 1,
                )
            } else {
                rSelect(
                    input = input,
                    i = i,
                    leftEndpoint = pivotIndex + 1,
                    rightEndpoint = rightEndpoint,
                )
            }
        }
    }

    private fun executePartition(
        input: Array<Long>,
        pivotIndex: Int,
        rightEndpoint: Int,
    ): Int {
        val pivot = input[pivotIndex]
        var i = pivotIndex + 1
        for (j in pivotIndex + 1..rightEndpoint) {
            if (input[j] < pivot) {
                val iValue = input[i]
                input[i] = input[j]
                input[j] = iValue
                i++
            }
        }
        input[pivotIndex] = input[i - 1]
        input[i - 1] = pivot

        return i - 1
    }

    fun dSelect(
        input: Array<Long>,
        i: Int,
    ): Long {
        return if (input.size <= 1) {
            input[0]
        } else {
            val medians = input.toList()
                .chunked(5)
                .map {
                    it.sorted()[it.size / 2]
                }.toTypedArray()

            val medianOfMedians = dSelect(medians, input.size / 10)
            val medianOfMediansIndex = input.indexOf(medianOfMedians)
            medianOfMediansIndex.let {
                val leftEndpointValue = input[0]
                input[0] = input[it]
                input[it] = leftEndpointValue
            }

            val pivotIndex = executePartition(
                input = input,
                pivotIndex = 0,
                rightEndpoint = input.lastIndex,
            )

            return if (pivotIndex + 1 == i) {
                input[pivotIndex]
            } else if (pivotIndex + 1 > i) {
                dSelect(
                    input = input.take(pivotIndex).toTypedArray(),
                    i = i
                )
            } else {
                dSelect(
                    input = input.drop(pivotIndex + 1).toTypedArray(),
                    i = i - (pivotIndex + 1),
                )
            }
        }
    }
}
