import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static int[] readIntArray(String fileName) throws IOException {
        final InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final List<Integer> numbers = reader.lines()
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
            return numbers.stream().mapToInt(i -> i).toArray();
        }
    }

    public static long[] readLongArray(String fileName) throws IOException {
        final InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final List<Long> numbers = reader.lines()
                    .map(String::trim)
                    .map(Long::parseLong)
                    .toList();
            return numbers.stream().mapToLong(i -> i).toArray();
        }
    }

    public static String readDecimal(String fileName) throws IOException {
        final InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .collect(Collectors.joining());
        }
    }

    public static String readEdge(String fileName) throws IOException {
        final InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(String::trim)
                    .collect(Collectors.joining("\n"));
        }
    }
}
