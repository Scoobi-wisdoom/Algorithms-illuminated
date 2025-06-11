import kotlin.Int.Companion.MIN_VALUE

class Chapter8Problem10 {
    fun getKosarajuSccNumbers(adjacencyList: String): IntArray {
        val vertexCount = adjacencyList.lines().maxOf { line ->
            line.split(" ")
                .maxBy { it.toInt() }
                .toInt()
        }

        val reverseAdjacencyList = adjacencyList.lineSequence()
            .joinToString(separator = "\n") {
                it.split(" ")
                    .reversed()
                    .joinToString(separator = " ")
            }
        val reverseEdgeFValues = getTopologicalSortFValues(getEdge(reverseAdjacencyList))

        val edge = getEdge(adjacencyList)
        val explored = List(vertexCount) { false }.toMutableList()
        val sccNumbers = List(vertexCount) { MIN_VALUE }.toMutableList()

        fun sccNumberDfs(
            index: Int,
            currentSccNumber: Int,
        ) {
            explored[index] = true
            sccNumbers[index] = currentSccNumber
            edge[index + 1]?.forEach {
                val vertexIndex = it - 1
                if (!explored[vertexIndex]) {
                    sccNumberDfs(
                        index = vertexIndex,
                        currentSccNumber = currentSccNumber,
                    )
                }
            }
        }

        var sccNumber = 0
        for (fValue in 1..vertexCount) {
            val vertexIndex = reverseEdgeFValues.indexOf(fValue)
            if (!explored[vertexIndex]) {
                sccNumberDfs(
                    index = vertexIndex,
                    currentSccNumber = ++sccNumber,
                )
            }
        }

        check(MIN_VALUE !in sccNumbers) { "sccNumbers have not been all set." }
        return sccNumbers.toIntArray()
    }

    private fun getEdge(adjacencyList: String): Map<Int, Set<Int>> {
        val edge = adjacencyList.lines()
            .map {
                it.split(" ")
                    .map(String::toInt)
            }
            .groupBy(keySelector = { it[0] }, valueTransform = { it[1] })
            .mapValues { (_, vertices) -> vertices.toSet() }
        return edge
    }

    fun getTopologicalSortFValues(edge: Map<Int, Set<Int>>): IntArray {
        val vertexCount = maxOf(edge.keys.max(), edge.values.flatten().max())

        val explored = List(vertexCount) { false }.toMutableList()
        val fValues = List(vertexCount) { MIN_VALUE }.toMutableList()
        var currentLabel = vertexCount

        fun fValueDfs(index: Int) {
            explored[index] = true
            edge[index + 1]?.forEach {
                val vertexIndex = it - 1
                if (!explored[vertexIndex]) fValueDfs(vertexIndex)
            }
            fValues[index] = currentLabel
            currentLabel--
        }

        repeat(vertexCount) { i ->
            if (!explored[i]) {
                fValueDfs(i)
            }
        }

        check(MIN_VALUE !in fValues) { "fValues have not been all set." }
        return fValues.toIntArray()
    }
}
