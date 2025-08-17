import Chapter12Problem4.CustomChainingHashTable
import Chapter12Problem4.CustomOpenAddressingHashTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.util.concurrent.CompletableFuture
import kotlin.test.assertEquals

class Chapter12Problem4Test {
    @Test
    fun authorTestCase1() {
        // given
        val input = longArrayOf(
            -3,
            -1,
            1,
            2,
            9,
            11,
            7,
            6,
            2,
        )
        val kotlinCustomChainingHashTable = CustomChainingHashTable(input)
        val kotlinCustomOpenAddressingHashTable = CustomOpenAddressingHashTable(input)
        val javaCustomChainingHashTable = JavaChapter12Problem4.CustomChainingHashTable(input)
        val javaCustomOpenAddressingHashTable = JavaChapter12Problem4.CustomOpenAddressingHashTable(input)

        // when/then
        assertAll(
            { assertEquals(8, (3L..10).count { kotlinCustomChainingHashTable.getTwoSum(it) }) },
            { assertEquals(8, (3L..10).count { kotlinCustomOpenAddressingHashTable.getTwoSum(it) }) },

            { assertEquals(8, (3L..10).count { javaCustomChainingHashTable.getTwoSum(it) }) },
            { assertEquals(8, (3L..10).count { javaCustomOpenAddressingHashTable.getTwoSum(it) }) },
        )
    }

    @Test
    fun authorTestCase2() = runBlocking {
        // given
        val input = Helper.readLongArray("problem12.4.txt")
        val targets = -10000L..10000
        val kotlinCustomChainingHashTable = CustomChainingHashTable(input)
        val kotlinCustomOpenAddressingHashTable = CustomOpenAddressingHashTable(input)
        val javaCustomChainingHashTable = JavaChapter12Problem4.CustomChainingHashTable(input)
        val javaCustomOpenAddressingHashTable = JavaChapter12Problem4.CustomOpenAddressingHashTable(input)

        // when
        val kotlinChainingActualAsync = async(Dispatchers.Default) {
            val start = System.currentTimeMillis()
            val result = targets.count { kotlinCustomChainingHashTable.getTwoSum(it) }
            Pair(result, System.currentTimeMillis() - start)
        }
        val kotlinOpenAddressingActualAsync = async(Dispatchers.Default) {
            val start = System.currentTimeMillis()
            val result = targets.count { kotlinCustomOpenAddressingHashTable.getTwoSum(it) }
            Pair(result, System.currentTimeMillis() - start)
        }
        val javaChainingActualAsync = CompletableFuture.supplyAsync {
            val start = System.currentTimeMillis()
            val result = targets.count { javaCustomChainingHashTable.getTwoSum(it) }
            Pair(result, System.currentTimeMillis() - start)
        }
        val javaOpenAddressingActualAsync = CompletableFuture.supplyAsync {
            val start = System.currentTimeMillis()
            val result = targets.count { javaCustomOpenAddressingHashTable.getTwoSum(it) }
            Pair(result, System.currentTimeMillis() - start)
        }

        val (kotlinChainingActual, kotlinChainingSpentTime) = kotlinChainingActualAsync.await()
        val (kotlinOpenAddressingActual, kotlinOpenAddressingSpentTime) = kotlinOpenAddressingActualAsync.await()
        val (javaChainingActual, javaChainingSpentTime) = javaChainingActualAsync.get()
        val (javaOpenAddressingActual, javaOpenAddressingSpentTime) = javaOpenAddressingActualAsync.get()

        // then
        assertAll(
            { assertEquals(427, kotlinChainingActual) },
            { assertEquals(427, kotlinOpenAddressingActual) },
            { assertTrue(kotlinOpenAddressingSpentTime < kotlinChainingSpentTime) },

            { assertEquals(427, javaChainingActual) },
            { assertEquals(427, javaOpenAddressingActual) },
            { assertTrue(javaOpenAddressingSpentTime < javaChainingSpentTime) },
        )
    }
}
