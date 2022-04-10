package com.github.pareronia.atcoder.abc._246.a;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

/**
 * A - Four Points
 * @see <a href="https://atcoder.jp/contests/abc246/tasks/abc246_a">https://atcoder.jp/contests/abc246/tasks/abc246_a</a>
 */
public class Main {

    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.in = in;
        this.out = out;
    }
    
    private static class Point {
        private final int x;
        private final int y;
        
        private Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        public static Point at(final int x, final int y) {
            return new Point(x, y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final int x1 = sc.nextInt();
        final int y1 = sc.nextInt();
        final int x2 = sc.nextInt();
        final int y2 = sc.nextInt();
        final int x3 = sc.nextInt();
        final int y3 = sc.nextInt();
        final IntSummaryStatistics summaryX = IntStream.of(x1, x2, x3).summaryStatistics();
        final IntSummaryStatistics summaryY = IntStream.of(y1, y2, y3).summaryStatistics();
        final List<Point> all = new ArrayList<>(List.of(
                Point.at(summaryX.getMin(), summaryY.getMin()),
                Point.at(summaryX.getMin(), summaryY.getMax()),
                Point.at(summaryX.getMax(), summaryY.getMin()),
                Point.at(summaryX.getMax(), summaryY.getMax())
        ));
        final List<Point> present = List.of(
                Point.at(x1, y1),
                Point.at(x2, y2),
                Point.at(x3, y3)
        );
        all.removeAll(present);
        final String ans = String.format("%d %d", all.get(0).x, all.get(0).y);
        this.out.println(ans);
    }
    
    public void solve() {
        try (final FastScanner sc = new FastScanner(this.in)) {
            final int numberOfTestCases = isSample() ? sc.nextInt() : 1;
            for (int i = 1; i <= numberOfTestCases; i++) {
                handleTestCase(sc, i);
            }
        }
    }

    public static void main(final String[] args) throws IOException, URISyntaxException {
        final boolean sample = isSample();
        final InputStream is;
        final PrintStream out;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        long timerStart = 0;
        if (sample) {
            is = Main.class.getResourceAsStream("sample.in");
            out = new PrintStream(baos, true);
            timerStart = System.nanoTime();
        } else {
            is = System.in;
            out = System.out;
        }
        
        new Main(sample, is, out).solve();
    	
        if (sample) {
            final long timeSpent = (System.nanoTime() - timerStart) / 1_000;
            final double time;
            final String unit;
            if (timeSpent < 1_000) {
                time = timeSpent;
                unit = "µs";
            } else if (timeSpent < 1_000_000) {
                time = timeSpent / 1_000.0;
                unit = "ms";
            } else {
                time = timeSpent / 1_000_000.0;
                unit = "s";
            }
            final Path path
                    = Paths.get(Main.class.getResource("sample.out").toURI());
            final List<String> expected = Files.readAllLines(path);
            final List<String> actual = asList(baos.toString().split("\\r?\\n"));
            if (!expected.equals(actual)) {
                throw new AssertionError(String.format(
                        "Expected %s, got %s", expected, actual));
            }
            actual.forEach(System.out::println);
            System.out.println(String.format("took: %.3f %s", time, unit));
        }
    }

    private static boolean isSample() {
        try {
            return "sample".equals(System.getProperty("atcoder"));
        } catch (final SecurityException e) {
            return false;
        }
    }

    private static final class FastScanner implements Closeable {
        private final BufferedReader br;
        private StringTokenizer st;
        
        public FastScanner(final InputStream in) {
            this.br = new BufferedReader(new InputStreamReader(in));
            st = new StringTokenizer("");
        }
        
        public String next() {
            while (!st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }
    
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
        @Override
        public void close() {
            try {
                this.br.close();
            } catch (final IOException e) {
                // ignore
            }
        }
    }
}
