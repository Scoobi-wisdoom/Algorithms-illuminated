import Chapter12Problem4.Constants.FIFTY_PERCENT
import java.util.LinkedList
import kotlin.Long.Companion.MIN_VALUE
import kotlin.math.abs

class Chapter12Problem4 {

    class CustomChainingHashTable(numbers: LongArray) {
        private val hashTable = Array(calculateHashTableSize(numbers)) { LinkedList<Long>() }.apply {
            numbers.forEach { number ->
                val linkedList = this[getHashCode(number, this.size)]
                linkedList.add(number)
            }
        }

        fun getTwoSum(targetSum: Long): Boolean {
            hashTable.forEach { linkedList ->
                linkedList.forEach { element ->
                    val complement = targetSum - element
                    if (
                        complement != element &&
                        hashTable[getHashCode(complement, hashTable.size)].contains(complement)
                    ) {
                        return true
                    }
                    if (complement == element && linkedList.count { it == complement } > 1) {
                        return true
                    }
                }
            }
            return false
        }
    }

    class CustomOpenAddressingHashTable(numbers: LongArray) {
        private val hashTable = Array(calculateHashTableSize(numbers)) { MIN_VALUE }.apply {
            numbers.forEach { number ->
                val index = getHashCode(number, this.size)
                for (i in 0 until this.size) {
                    val linearProbingIndex = index + i
                    if (this[linearProbingIndex] == MIN_VALUE) {
                        this[linearProbingIndex] = number
                        break
                    }
                }
            }
        }

        fun getTwoSum(targetSum: Long): Boolean {
            hashTable.forEach { element ->
                val complement = targetSum - element
                val index = getHashCode(complement, hashTable.size)
                if (element == MIN_VALUE || hashTable[index] == MIN_VALUE) {
                    return@forEach
                }

                val startOffset = if (complement == element) 1 else 0
                for (i in startOffset until hashTable.size) {
                    val linearProbingIndex = index + i
                    if (linearProbingIndex > hashTable.lastIndex || hashTable[linearProbingIndex] == MIN_VALUE) {
                        break
                    }
                    if (hashTable[linearProbingIndex] == complement) {
                        return true
                    }
                }
            }
            return false
        }
    }

    private object Constants {
        const val FIFTY_PERCENT = 0.5
    }

    companion object {
        fun calculateHashTableSize(numbers: LongArray): Int {
            val hashTableLoad = FIFTY_PERCENT
            return (numbers.size / hashTableLoad).toInt()
        }

        fun getHashCode(key: Long, hashTableSize: Int): Int = (abs(key) % hashTableSize).toInt()
    }
}
