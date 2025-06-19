import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaChapter9Problem8 {
    public static int TWO = 2;

    public int[] getDijkstraLengths(String graph) {
        int[] vertices = graph.lines()
                .mapToInt(line -> Integer.parseInt(line.split("\t", TWO)[0]))
                .toArray();
        if (vertices[0] != 1)
            throw new IllegalArgumentException("Graph should contain vertex 1 as the starting vertex.");

        Set<Integer> exploredVertices = new HashSet<>();
        exploredVertices.add(vertices[0]);
        int[] dijkstraLengths = new int[vertices.length];

        Map<AbstractMap.SimpleEntry<Integer, Integer>, Integer> edgeToLength = graph.lines()
                .map(line -> line.trim().split("\t", TWO))
                .flatMap(parts -> {
                    if (parts.length < TWO) return Stream.empty();
                    int head = Integer.parseInt(parts[0]);
                    String[] tailLengthEntries = parts[1].split("\t");

                    return Arrays.stream(tailLengthEntries)
                            .map(entry -> entry.split(","))
                            .filter(arr -> arr.length == TWO)
                            .map(arr -> new AbstractMap.SimpleEntry<>(
                                    new AbstractMap.SimpleEntry<>(head, Integer.parseInt(arr[0])),
                                    Integer.parseInt(arr[1])
                            ));
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> b,
                        LinkedHashMap::new
                ));

        while (edgeToLength.keySet().stream().anyMatch(edge ->
                isEdgeToCheck(edge, exploredVertices))
        ) {
            Map.Entry<AbstractMap.SimpleEntry<Integer, Integer>, Integer> selectedEdge = edgeToLength.entrySet().stream()
                    .filter(entry -> isEdgeToCheck(entry.getKey(), exploredVertices))
                    .min(Comparator.comparingInt(entry ->
                            dijkstraLengths[entry.getKey().getKey() - 1] + entry.getValue()
                    ))
                    .orElseThrow();
            Integer head = selectedEdge.getKey().getKey();
            Integer tail = selectedEdge.getKey().getValue();
            exploredVertices.add(tail);
            dijkstraLengths[tail - 1] = dijkstraLengths[head - 1] + selectedEdge.getValue();
        }
        return dijkstraLengths;
    }

    private boolean isEdgeToCheck(AbstractMap.SimpleEntry<Integer, Integer> edge, Set<Integer> exploredVertices) {
        return exploredVertices.contains(edge.getKey()) && !exploredVertices.contains(edge.getValue());
    }
}
