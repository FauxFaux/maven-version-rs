import org.apache.maven.artifact.versioning.ComparableVersion;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class MirroredTest {
    @Test
    public void foo() {
        check_all_versions_order("0-RELEASE", "0.1.1", "1.0.0");
        check_all_versions_order("0-RELEASE", "0.0", "0.0.0", "RELEASE");
    }

    class ComparableVersionThenString implements Comparable<ComparableVersionThenString> {
        private final ComparableVersion inner;
        private final String original;

        ComparableVersionThenString(String val) {
            original = val;
            inner = new ComparableVersion(val);
        }

        @Override
        public int compareTo(ComparableVersionThenString o) {
            final int first = inner.compareTo(o.inner);
            if (0 != first) {
                return first;
            }
            final int second = inner.getCanonical().compareTo(o.inner.getCanonical());
            if (0 != second) {
                return second;
            }
            return original.compareTo(o.original);
        }

        @Override
        public String toString() {
            return inner.toString();
        }
    }

    void check_all_versions_order(String... versions) {
        final List<ComparableVersionThenString> c = Arrays.stream(versions)
                .map(ComparableVersionThenString::new)
                .collect(Collectors.toList());

        for (int i = 1; i < c.size(); ++i) {
            final ComparableVersionThenString low = c.get(i - 1);

            for (int j = i; j < c.size(); ++j) {
                final ComparableVersionThenString high = c.get(j);
                assertThat(low, lessThan(high));
                assertThat(high, greaterThan(low));
            }
        }
    }
}
