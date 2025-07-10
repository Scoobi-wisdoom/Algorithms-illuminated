import Chapter9Problem8.Constants.TWO
import kotlin.Int.Companion.MAX_VALUE

class Chapter9Problem8 {
    fun getDijkstraLengths(graph: String): IntArray {
        val vertices = graph.lines().map { it.split("\t").first().toInt() }
        val exploredVertices = mutableSetOf<Int>().apply {
            vertices.find { it == 1 }
                ?.let { add(it) }
                ?: throw IllegalArgumentException("Graph should contain vertex 1 as the starting vertex.")
        }
        val dijkstraLengths = List(vertices.size) { MAX_VALUE }.toMutableList().apply {
            this[0] = 0
        }

        val edgeToLength = graph.lines()
            .map { it.trim().split("\t", limit = TWO) }
            .flatMap { (tail, headLengths) ->
                headLengths.split("\t")
                    .map { it.split(",") }
                    .filter { it.size == TWO }
                    .map { (head, length) ->
                        Pair(tail.toInt(), head.toInt()) to length.toInt()
                    }
            }.toMap()

        fun isEdgeToCheck(edge: Pair<Int, Int>) = edge.first in exploredVertices && edge.second !in exploredVertices
        while (edgeToLength.keys.any { isEdgeToCheck(it) }) {
            val selectedEdge = edgeToLength.filterKeys { isEdgeToCheck(it) }
                .minBy { (headAndTail, length) ->
                    dijkstraLengths[headAndTail.first - 1] + length
                }

            val (head, tail) = selectedEdge.key
            exploredVertices.add(tail)
            dijkstraLengths.apply {
                this[tail - 1] = this[head - 1] + selectedEdge.value
            }
        }

        return dijkstraLengths.toIntArray()
    }

    private object Constants {
        const val TWO = 2
    }
}
