import org.apache.maven.artifact.versioning.ComparableVersion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Sort {
    public static void main(String... args) throws IOException {
        Files.lines(Paths.get(args[0]))
                .map(ComparableVersion::new)
                .sorted()
                .forEach(System.out::println);
    }
}
