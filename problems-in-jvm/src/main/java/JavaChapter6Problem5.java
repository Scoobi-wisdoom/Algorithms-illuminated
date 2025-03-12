import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JavaChapter6Problem5 {
    public long rSelect(
            long[] input,
            int i,
            int leftEndpoint,
            int rightEndpoint
    ) {
        if (leftEndpoint >= rightEndpoint) {
            return input[leftEndpoint];
        } else {
            int chosenPivotIndex = new Random().nextInt(rightEndpoint - leftEndpoint + 1) + leftEndpoint;
            final long leftEndpointValue = input[leftEndpoint];
            input[leftEndpoint] = input[chosenPivotIndex];
            input[chosenPivotIndex] = leftEndpointValue;

            int pivotIndex = executePartition(
                    input,
                    leftEndpoint,
                    rightEndpoint
            );

            if (pivotIndex + 1 == i) {
                return input[pivotIndex];
            } else if (pivotIndex + 1 > i) {
                return rSelect(
                        input,
                        i,
                        0,
                        pivotIndex - 1
                );
            } else {
                return rSelect(
                        input,
                        i,
                        pivotIndex + 1,
                        rightEndpoint
                );
            }
        }
    }

    private int executePartition(
            long[] input,
            int pivotIndex,
            int rightEndpoint
    ) {
        final long pivot = input[pivotIndex];
        int i = pivotIndex + 1;
        for (int j = pivotIndex + 1; j < rightEndpoint + 1; j++) {
            if (input[j] < pivot) {
                long iValue = input[i];
                input[i] = input[j];
                input[j] = iValue;
                i++;
            }
        }
        input[pivotIndex] = input[i - 1];
        input[i - 1] = pivot;
        return i - 1;
    }

    public long dSelect(
            long[] input,
            int i
    ) {
        if (input.length <= 1) {
            return input[0];
        } else {
            final List<Long> medians = new ArrayList<>();
            for (int j = 0; j < input.length; j += 5) {
                int end = Math.min(j + 5, input.length);
                long[] chunk = Arrays.copyOfRange(input, j, end);
                Arrays.sort(chunk);
                medians.add(chunk[chunk.length / 2]);
            }

            final long medianOfMedians = dSelect(
                    medians.stream().mapToLong(Long::longValue).toArray(),
                    medians.size() / 2
            );

            int medianOfMediansIndex = Arrays.stream(input).boxed().toList().indexOf(medianOfMedians);
            long leftEndpointValue = input[0];
            input[0] = input[medianOfMediansIndex];
            input[medianOfMediansIndex] = leftEndpointValue;

            int pivotIndex = executePartition(
                    input,
                    0,
                    input.length - 1
            );

            if (pivotIndex + 1 == i) {
                return input[pivotIndex];
            } else if (pivotIndex + 1 > i) {
                return dSelect(
                        Arrays.copyOfRange(input, 0, pivotIndex),
                        i
                );
            } else {
                return dSelect(
                        Arrays.copyOfRange(input, pivotIndex + 1, input.length),
                        i - (pivotIndex + 1)
                );
            }
        }
    }
}
