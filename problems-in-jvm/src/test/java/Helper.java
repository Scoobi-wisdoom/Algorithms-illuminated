import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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
}
