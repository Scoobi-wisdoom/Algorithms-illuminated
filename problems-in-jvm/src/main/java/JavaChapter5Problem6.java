import java.util.Random;

public class JavaChapter5Problem6 {
    public enum PivotStrategy {
        FIRST,
        LAST,
        RANDOM,
        MEDIAN_OF_THREE,
        ;

        public int choosePivot(
                int[] input,
                int leftEndpoint,
                int rightEndpoint
        ) {
            return switch (this) {
                case FIRST:
                    yield leftEndpoint;
                case LAST:
                    yield rightEndpoint;
                case RANDOM:
                    yield new Random().nextInt(rightEndpoint - leftEndpoint + 1) + leftEndpoint;
                case MEDIAN_OF_THREE: {
                    final int middleIndex = (rightEndpoint + leftEndpoint) / 2;

                    final int first = input[leftEndpoint];
                    final int middle = input[middleIndex];
                    final int last = input[rightEndpoint];

                    final int pivotIndex;
                    if ((first < Math.max(middle, last)) && (first > Math.min(middle, last))
                    ) {
                        pivotIndex = leftEndpoint;
                    } else if ((middle < Math.max(first, last)) && (middle > Math.min(first, last))) {
                        pivotIndex = middleIndex;
                    } else {
                        pivotIndex = rightEndpoint;
                    }
                    yield pivotIndex;
                }
            };
        }
    }

    public long quickSort(
            PivotStrategy pivotStrategy,
            int[] input,
            int leftEndpoint,
            int rightEndpoint
    ) {
        if (leftEndpoint >= rightEndpoint) {
            return 0;
        } else {
            final int pivotIndex = pivotStrategy.choosePivot(
                    input,
                    leftEndpoint,
                    rightEndpoint
            );
            final int originalLeftValue = input[leftEndpoint];
            input[leftEndpoint] = input[pivotIndex];
            input[pivotIndex] = originalLeftValue;

            final int determinedPivotIndex = executePartition(
                    input,
                    leftEndpoint,
                    rightEndpoint
            );

            return rightEndpoint - leftEndpoint +
                    quickSort(
                            pivotStrategy,
                            input,
                            leftEndpoint,
                            determinedPivotIndex - 1
                    ) +
                    quickSort(
                            pivotStrategy,
                            input,
                            determinedPivotIndex + 1,
                            rightEndpoint
                    );
        }
    }

    private int executePartition(
            int[] input,
            int pivotIndex,
            int rightEndpoint
    ) {
        final int pivot = input[pivotIndex];
        int i = pivotIndex + 1;
        for (int j = pivotIndex + 1; j < rightEndpoint + 1; j++) {
            if (input[j] < pivot) {
                int iValue = input[i];
                input[i] = input[j];
                input[j] = iValue;
                i++;
            }
        }

        input[pivotIndex] = input[i - 1];
        input[i - 1] = pivot;

        return i - 1;
    }
}
