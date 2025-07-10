import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

public class JavaChapter10Problem8 {
    private static final int TWO = 2;

    public record Edge(
            int head,
            int length
    ) {
    }

    public static final class CustomHeap {
        private static final int STARTING_VERTEX = 1;
        private final List<CustomHeapElement> _heapArray = new ArrayList<>();
        private final Map<Integer, Integer> _vertexToIndex = new HashMap<>();

        public CustomHeap(int[] vertices) {
            if (vertices.length == 0) {
                throw new IllegalArgumentException("Vertices cannot be empty.");
            }
            if (vertices[0] != STARTING_VERTEX) {
                throw new IllegalArgumentException("First vertex must be 1.");
            }
            for (int vertex : vertices) {
                if (vertex == STARTING_VERTEX) {
                    this._heapArray.add(new CustomHeapElement(vertex, 0));
                } else {
                    this._heapArray.add(new CustomHeapElement(vertex, MAX_VALUE));
                }
            }
            for (int i = 0; i < this._heapArray.size(); i++) {
                this._vertexToIndex.put(this._heapArray.get(i).vertex(), i);
            }
        }

        public List<CustomHeapElement> getHeapArray() {
            return new ArrayList<>(this._heapArray);
        }

        public boolean isEmpty() {
            return this._heapArray.isEmpty();
        }

        public CustomHeapElement extractMin() {
            if (this._heapArray.size() > 1) {
                swap(0, this._heapArray.size() - 1);
                CustomHeapElement last = this._heapArray.remove(this._heapArray.size() - 1);
                this._vertexToIndex.remove(last.vertex);
                bubbleDown(1);
                return last;
            } else {
                this._vertexToIndex.clear();
                return this._heapArray.remove(0);
            }
        }

        private void swap(int index1, int index2) {
            if (index1 != index2) {
                CustomHeapElement temp = this._heapArray.get(index1);
                this._heapArray.set(index1, this._heapArray.get(index2));
                this._heapArray.set(index2, temp);
                this._vertexToIndex.put(this._heapArray.get(index1).vertex, index1);
                this._vertexToIndex.put(this._heapArray.get(index2).vertex, index2);
            }
        }

        private void bubbleDown(int currentPosition) {
            if (currentPosition <= 0 || currentPosition > this._heapArray.size()) {
                throw new IllegalArgumentException("Index is out of bounds.");
            }
            int leftChildPosition = currentPosition * TWO;
            if (leftChildPosition > this._heapArray.size()) {
                return;
            }
            int rightChildPosition = leftChildPosition + 1;
            int smallerChildPosition;
            if (
                    rightChildPosition > this._heapArray.size() ||
                            this._heapArray.get(leftChildPosition - 1).key() <= this._heapArray.get(rightChildPosition - 1).key()
            ) {
                smallerChildPosition = leftChildPosition;
            } else {
                smallerChildPosition = rightChildPosition;
            }

            if (this._heapArray.get(currentPosition - 1).key > this._heapArray.get(smallerChildPosition - 1).key) {
                swap(currentPosition - 1, smallerChildPosition - 1);
                bubbleDown(smallerChildPosition);
            }
        }

        public void insert(CustomHeapElement element) {
            this._heapArray.add(element);
            this._vertexToIndex.put(element.vertex(), this._heapArray.size() - 1);
            bubbleUp(this._heapArray.size());
        }

        private void bubbleUp(int currentPosition) {
            if (currentPosition <= 0 || currentPosition > this._heapArray.size()) {
                throw new IllegalArgumentException("Index is out of bounds.");
            }
            int parentPosition = currentPosition / TWO;
            if (
                    parentPosition > 0 &&
                            this._heapArray.get(currentPosition - 1).key() < this._heapArray.get(parentPosition - 1).key
            ) {
                swap(currentPosition - 1, parentPosition - 1);
                bubbleUp(parentPosition);
            }
        }

        public CustomHeapElement remove(int vertex) {
            Integer index = this._vertexToIndex.get(vertex);
            if (index == null) {
                throw new IllegalArgumentException("Vertex " + vertex + " is not in the heap.");
            }
            if (index == this._heapArray.size() - 1) {
                CustomHeapElement removedElement = this._heapArray.remove(this._heapArray.size() - 1);
                this._vertexToIndex.remove(removedElement.vertex());
                return removedElement;
            } else {
                swap(index, this._heapArray.size() - 1);
                CustomHeapElement removedElement = this._heapArray.remove(this._heapArray.size() - 1);
                this._vertexToIndex.remove(removedElement.vertex());
                bubbleDown(index + 1);
                return removedElement;
            }
        }
    }

    public record CustomHeapElement(
            int vertex,
            int key
    ) {
    }

    public int[] getDijkstraLengths(String graph) {
        int[] vertices = graph.lines()
                .mapToInt(line -> Integer.parseInt(line.split("\t", TWO)[0]))
                .toArray();
        int[] dijkstraLengths = new int[vertices.length];
        Map<Integer, List<Edge>> tailToEdges = graph.lines()
                .map(line -> line.trim().split("\t", TWO))
                .filter(parts -> parts.length == TWO)
                .collect(Collectors.toMap(
                        parts -> Integer.parseInt(parts[0]),
                        parts -> Arrays.stream(parts[1].split("\t"))
                                .map(headAndLength -> {
                                    String[] split = headAndLength.split(",");
                                    return new Edge(
                                            Integer.parseInt(split[0]),
                                            Integer.parseInt(split[1])
                                    );
                                })
                                .toList()
                ));

        Set<Integer> exploredVertices = new HashSet<>();
        CustomHeap customHeap = new CustomHeap(vertices);
        while (!customHeap.isEmpty()) {
            CustomHeapElement chosenElement = customHeap.extractMin();

            dijkstraLengths[chosenElement.vertex() - 1] = chosenElement.key();
            exploredVertices.add(chosenElement.vertex());

            List<Edge> edges = tailToEdges.get(chosenElement.vertex());
            if (edges != null) {
                edges.forEach(edge -> {
                    if (!exploredVertices.contains(edge.head())) {
                        CustomHeapElement customHeapElement = customHeap.remove(edge.head());
                        customHeap.insert(
                                new CustomHeapElement(
                                        customHeapElement.vertex,
                                        min(customHeapElement.key(), dijkstraLengths[chosenElement.vertex - 1] + edge.length())
                                ));
                    }
                });
            }
        }

        return dijkstraLengths;
    }
}
