import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.MIN_VALUE;

public class JavaChapter8Problem10 {
    public int[] getKosarajuSccNumbers(String adjacencyList) {
        int vertexCount = Arrays.stream(adjacencyList.split("\n"))
                .mapToInt(line -> Arrays.stream(line.split(" "))
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0)
                )
                .max()
                .orElse(0);

        String reverseAdjacencyList = adjacencyList.lines()
                .map(line -> {
                    String[] split = line.split(" ");
                    Collections.reverse(Arrays.asList(split));
                    return String.join(" ", split);
                })
                .collect(Collectors.joining("\n"));
        ArrayList<Integer> reverseEdgeFValues = getTopologicalSortFValues(getEdge(reverseAdjacencyList));
        Map<Integer, Set<Integer>> edge = getEdge(adjacencyList);
        boolean[] explored = new boolean[vertexCount];
        int[] sccNumbers = new int[vertexCount];

        int sccNumber = 0;
        for (int fValue = 1; fValue < vertexCount + 1; fValue++) {
            int vertexIndex = reverseEdgeFValues.indexOf(fValue);
            if (!explored[vertexIndex]) {
                sccNumber = sccNumberDfs(explored, sccNumbers, edge, vertexIndex, ++sccNumber);
            }
        }
        return sccNumbers;
    }

    private int sccNumberDfs(
            boolean[] explored,
            int[] sccNumbers,
            Map<Integer, Set<Integer>> edge,
            int index,
            int currentSccNumber
    ) {
        explored[index] = true;
        sccNumbers[index] = currentSccNumber;
        Set<Integer> neighbors = edge.get(index + 1);
        if (neighbors != null) {
            for (Integer neighbor : neighbors) {
                int neighborIndex = neighbor - 1;
                if (!explored[neighborIndex]) {
                    currentSccNumber = sccNumberDfs(explored, sccNumbers, edge, neighborIndex, currentSccNumber);
                }
            }
        }
        return currentSccNumber;
    }

    private Map<Integer, Set<Integer>> getEdge(String adjacencyList) {
        return adjacencyList.lines()
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .toList())
                .collect(Collectors.groupingBy(
                        list -> list.get(0),
                        Collectors.mapping(
                                list -> list.get(1),
                                Collectors.toSet()
                        )
                ));
    }

    public ArrayList<Integer> getTopologicalSortFValues(Map<Integer, Set<Integer>> edge) {
        int vertexCount = Math.max(
                Collections.max(
                        edge.keySet()),
                edge.values().stream()
                        .flatMap(Collection::stream)
                        .max(Integer::compareTo)
                        .orElse(MIN_VALUE)
        );

        boolean[] explored = new boolean[vertexCount];
        int[] fValues = new int[vertexCount];

        int currentLabel = vertexCount;
        for (int i = 0; i < vertexCount; i++) {
            if (!explored[i]) {
                currentLabel = fValueDfs(explored, edge, fValues, currentLabel, i);
            }
        }

        return Arrays.stream(fValues)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int fValueDfs(
            boolean[] explored,
            Map<Integer, Set<Integer>> edge,
            int[] fValues,
            int currentLabel,
            int index
    ) {
        explored[index] = true;
        Set<Integer> neighbors = edge.get(index + 1);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                int neighborIndex = neighbor - 1;
                if (!explored[neighborIndex]) {
                    currentLabel = fValueDfs(explored, edge, fValues, currentLabel, neighborIndex);
                }
            }
        }
        fValues[index] = currentLabel;
        return --currentLabel;
    }
}
