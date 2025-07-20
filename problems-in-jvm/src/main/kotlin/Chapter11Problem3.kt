import java.util.PriorityQueue
import kotlin.math.max

class Chapter11Problem3 {
    class CustomAVLTree(
        key: Int,
    ) {
        var key: Int = key
            private set

        var size: Int = 1
            private set

        var height: Int = 0
            private set

        var left: CustomAVLTree? = null
            private set

        var right: CustomAVLTree? = null
            private set

        fun insert(value: Int): CustomAVLTree {
            if (value > key) {
                right = right?.insert(value) ?: CustomAVLTree(value)
            } else {
                left = left?.insert(value) ?: CustomAVLTree(value)
            }
            size++
            // By definition of insert, at least one of left and right are not null.
            height = max(right?.height ?: 0, left?.height ?: 0) + 1

            val leftHeight = left?.height ?: 0
            val rightHeight = right?.height ?: 0

            return when {
                rightHeight + 1 < leftHeight -> {
                    val newRoot = checkNotNull(left)
                    val newRootLeftHeight = newRoot.left?.height ?: 0
                    val newRootRightHeight = newRoot.right?.height ?: 0
                    if (newRootLeftHeight + 1 < newRootRightHeight) {
                        left = newRoot.rotateLeft()
                    }
                    rotateRight()
                }

                leftHeight + 1 < rightHeight -> {
                    val newRoot = checkNotNull(right)
                    val newRootLeftHeight = newRoot.left?.height ?: 0
                    val newRootRightHeight = newRoot.right?.height ?: 0
                    if (newRootLeftHeight > newRootRightHeight + 1) {
                        right = newRoot.rotateRight()
                    }
                    rotateLeft()
                }

                else -> this
            }
        }

        private fun rotateLeft(): CustomAVLTree {
            val newRoot = checkNotNull(right) { "Right node is null." }
            right = newRoot.left
            newRoot.left = this.apply {
                height = if (right == null && left == null) 0 else
                    max(right?.height ?: 0, left?.height ?: 0) + 1

                size = (left?.size ?: 0) + (right?.size ?: 0) + 1
            }

            return newRoot.apply {
                height = max(right?.height ?: 0, left?.height ?: 0) + 1

                size = (left?.size ?: 0) + (right?.size ?: 0) + 1
            }
        }

        private fun rotateRight(): CustomAVLTree {
            val newRoot = checkNotNull(left) { "Left node is null." }
            left = newRoot.right
            newRoot.right = this.apply {
                height = if (right == null && left == null) 0 else
                    max(right?.height ?: 0, left?.height ?: 0) + 1

                size = (left?.size ?: 0) + (right?.size ?: 0) + 1
            }

            return newRoot.apply {
                height = max(right?.height ?: 0, left?.height ?: 0) + 1
                size = (left?.size ?: 0) + (right?.size ?: 0) + 1
            }
        }

        fun select(position: Int): Int {
            require(0 < position && position <= size) { "Invalid position" }
            val rightSize = right?.size ?: 0
            return when {
                position == size - rightSize -> key
                position < size - rightSize -> checkNotNull(left) {
                    "Left node is null"
                }.select(position)

                else -> {
                    val leftSize = left?.size ?: 0
                    checkNotNull(right) {
                        "Right node is null"
                    }.select(position - leftSize - 1)
                }
            }
        }
    }

    fun getHeapKthMedians(numbers: IntArray): IntArray {
        val firstHalfHeap = PriorityQueue<Int>(Comparator.reverseOrder())
        val secondHalfHeap = PriorityQueue<Int>()

        val toIntArray = numbers.mapIndexed { index, number ->
            when {
                firstHalfHeap.isEmpty() -> firstHalfHeap.add(number)
                secondHalfHeap.isEmpty() -> {
                    if (number < firstHalfHeap.peek()) {
                        secondHalfHeap.add(firstHalfHeap.poll())
                        firstHalfHeap.add(number)
                    } else {
                        secondHalfHeap.add(number)
                    }
                }

                number > secondHalfHeap.peek() -> secondHalfHeap.add(number)
                else -> firstHalfHeap.add(number)
            }

            if (firstHalfHeap.size < secondHalfHeap.size) {
                firstHalfHeap.add(secondHalfHeap.poll())
            }
            if (firstHalfHeap.size > secondHalfHeap.size + 1) {
                secondHalfHeap.add(firstHalfHeap.poll())
            }

            firstHalfHeap.peek()
        }.toIntArray()
        return toIntArray
    }

    fun getSearchTreeKthMedians(numbers: IntArray): IntArray {
        var customSearchTree = CustomAVLTree(numbers.first())
        return numbers.mapIndexed { index, number ->
            if (index > 0) {
                customSearchTree = customSearchTree.insert(number)
            }
            customSearchTree.select((index + 2) / 2)
        }.toIntArray()
    }
}
