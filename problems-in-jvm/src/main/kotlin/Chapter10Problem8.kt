import Chapter10Problem8.Constants.STARTING_VERTEX
import Chapter10Problem8.Constants.TWO
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.min

class Chapter10Problem8 {

    class Edge(
        val head: Int,
        val length: Int,
    )

    class CustomHeap(vertices: List<Int>) {
        init {
            require(vertices.isNotEmpty()) { "Vertices cannot be empty." }
            require(vertices.first() == STARTING_VERTEX) { "First vertex must be 1." }
        }

        private val _heapArray = vertices.map {
            if (it == STARTING_VERTEX) Pair(it, 0) else
                Pair(it, MAX_VALUE)
        }.toMutableList()

        private val vertexToIndex = _heapArray.mapIndexed { index, element ->
            element.first to index
        }
            .toMap()
            .toMutableMap()

        val heapArray: List<Pair<Int, Int>>
            get() = _heapArray.toList()

        fun isEmpty(): Boolean = _heapArray.isEmpty()

        fun extractMin(): Pair<Int, Int> {
            return if (_heapArray.size > 1) {
                swap(0, _heapArray.lastIndex)
                _heapArray.removeLast().also { (vertex, _) ->
                    // O(1) not O(n) because it is hash based.
                    vertexToIndex.remove(vertex)
                    bubbleDown(1)
                }
            } else {
                _heapArray.removeFirst().also {
                    vertexToIndex.clear()
                }
            }
        }

        private fun bubbleDown(currentPosition: Int) {
            require(0 < currentPosition && currentPosition <= _heapArray.size) { "Index is out of bounds." }

            val leftChildPosition = currentPosition * TWO
            val rightChildPosition = (leftChildPosition + 1).let { if (it <= _heapArray.size) it else null }
            if (leftChildPosition > _heapArray.size)
                return

            val smallerChildPosition = rightChildPosition?.let {
                if (_heapArray[leftChildPosition - 1].second <= _heapArray[it - 1].second) leftChildPosition else
                    it
            } ?: leftChildPosition

            if (_heapArray[currentPosition - 1].second > _heapArray[smallerChildPosition - 1].second) {
                swap(currentPosition - 1, smallerChildPosition - 1)
                bubbleDown(smallerChildPosition)
            }
        }

        private fun swap(index1: Int, index2: Int) {
            if (index1 != index2) {
                _heapArray[index1] = _heapArray[index2].also { _heapArray[index2] = _heapArray[index1] }
                vertexToIndex[_heapArray[index1].first] = index1
                vertexToIndex[_heapArray[index2].first] = index2
            }
        }

        fun remove(vertex: Int): Pair<Int, Int> {
            val index = requireNotNull(vertexToIndex[vertex]) { "Vertex $vertex is not in the heap." }
            return if (index == _heapArray.lastIndex) {
                _heapArray.removeLast().also { (vertex, _) ->
                    vertexToIndex.remove(vertex)
                }
            } else {
                swap(index, _heapArray.lastIndex)
                _heapArray.removeLast().also { (vertex, _) ->
                    vertexToIndex.remove(vertex)
                    bubbleDown(index + 1)
                }
            }
        }

        fun insert(vertexAndKey: Pair<Int, Int>) {
            _heapArray.add(vertexAndKey)
            vertexToIndex[vertexAndKey.first] = _heapArray.lastIndex
            bubbleUp(_heapArray.size)
        }

        private fun bubbleUp(currentPosition: Int) {
            require(0 < currentPosition && currentPosition <= _heapArray.size) { "Index is out of bounds." }
            val parentPosition = currentPosition / TWO
            if (
                parentPosition > 0 &&
                _heapArray[currentPosition - 1].second < _heapArray[parentPosition - 1].second
            ) {
                swap(currentPosition - 1, parentPosition - 1)
                bubbleUp(parentPosition)
            }
        }
    }

    fun getDijkstraLengths(graph: String): IntArray {
        val vertices = graph.lines().map { it.split("\t").first().toInt() }
        val dijkstraLengths = List(vertices.size) { MAX_VALUE }.toMutableList()

        val tailToEdges = graph.lines()
            .map { it.trim().split("\t", limit = TWO) }
            .associate { (tail, headLengths) ->
                tail.toInt() to headLengths.split("\t")
                    .map {
                        val split = it.split(",")
                        Edge(
                            head = split.first().toInt(),
                            length = split.last().toInt(),
                        )
                    }
            }

        val exploredVertices = mutableSetOf<Int>()
        val customHeap = CustomHeap(vertices)
        while (!customHeap.isEmpty()) {
            val (chosenVertex, length) = customHeap.extractMin()

            dijkstraLengths[chosenVertex - 1] = length
            exploredVertices.add(chosenVertex)

            tailToEdges[chosenVertex]?.forEach {
                if (it.head !in exploredVertices) {
                    val (head, key) = customHeap.remove(it.head)
                    customHeap.insert(
                        Pair(
                            head,
                            min(key, dijkstraLengths[chosenVertex - 1] + it.length),
                        )
                    )
                }
            }
        }
        return dijkstraLengths.toIntArray()
    }

    private object Constants {
        const val STARTING_VERTEX = 1
        const val TWO = 2
    }
}
