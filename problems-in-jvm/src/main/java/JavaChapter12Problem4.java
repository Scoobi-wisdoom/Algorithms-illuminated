import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Long.MIN_VALUE;
import static java.lang.Math.abs;

public class JavaChapter12Problem4 {
    private static final double FIFTY_PERCENT = 0.5;

    public static int calculateHashTableSize(long[] numbers) {
        double hashTableLoad = FIFTY_PERCENT;
        return (int) (numbers.length / hashTableLoad);
    }

    public static int getHashCode(long key, int hashTableSize) {
        return (int) (abs(key) % hashTableSize);
    }


    public static final class CustomChainingHashTable {
        private final List<List<Long>> hashTable;

        public CustomChainingHashTable(long[] numbers) {
            int size = calculateHashTableSize(numbers);
            hashTable = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                hashTable.add(new LinkedList<>());
            }
            for (long number : numbers) {
                List<Long> bucket = hashTable.get(getHashCode(number, size));
                bucket.add(number);
            }
        }

        public boolean getTwoSum(long targetSum) {
            for (List<Long> bucket : hashTable) {
                for (long element : bucket) {
                    long complement = targetSum - element;
                    if (complement != element &&
                            hashTable.get(getHashCode(complement, hashTable.size())).contains(complement)
                    ) {
                        return true;
                    }
                    if (complement == element && bucket.stream().filter(x -> x == complement).count() > 1) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static final class CustomOpenAddressingHashTable {
        private final long[] hashTable;

        public CustomOpenAddressingHashTable(long[] numbers) {
            int size = calculateHashTableSize(numbers);
            hashTable = new long[size];
            Arrays.fill(hashTable, MIN_VALUE);

            for (long number : numbers) {
                int index = getHashCode(number, size);
                for (int i = 0; i < size; i++) {
                    int linearProbingIndex = index + i;
                    if (hashTable[linearProbingIndex] == MIN_VALUE) {
                        hashTable[linearProbingIndex] = number;
                        break;
                    }
                }
            }
        }

        public boolean getTwoSum(long targetSum) {
            for (long element : hashTable) {
                if (element == MIN_VALUE) continue;

                long complement = targetSum - element;
                int index = getHashCode(complement, hashTable.length);

                if (hashTable[index] == MIN_VALUE) continue;

                int startOffset = complement == element ? 1 : 0;
                for (int i = startOffset; i < hashTable.length; i++) {
                    int linearProbingIndex = index + i;

                    if (linearProbingIndex > hashTable.length - 1 ||
                            hashTable[linearProbingIndex] == MIN_VALUE
                    ) {
                        break;
                    }
                    if (hashTable[linearProbingIndex] == complement) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
