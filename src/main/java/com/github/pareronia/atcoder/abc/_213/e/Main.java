package com.github.pareronia.atcoder.abc._213.e;

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
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Supplier;

/**
 * E - Stronger Takahashi
 * @see <a href="https://atcoder.jp/contests/abc213/tasks/abc213_e">https://atcoder.jp/contests/abc213/tasks/abc213_e</a>
 */
public class Main {

    private static final Pair<Integer, Integer> N = Pair.of(-1, 0);
    private static final Pair<Integer, Integer> E = Pair.of(0, 1);
    private static final Pair<Integer, Integer> S = Pair.of(1, 0);
    private static final Pair<Integer, Integer> W = Pair.of(0, -1);
    private static final Set<Pair<Integer, Integer>> NESW = Set.of(N, E, S, W);
    
    private final boolean sample;
    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.sample = sample;
        this.in = in;
        this.out = out;
    }
    
    @SuppressWarnings("unused")
    private void log(final Supplier<Object> supplier) {
        if (!sample) {
            return;
        }
        System.out.println(supplier.get());
    }
    
    private boolean inBounds(final char[][] g, final int r, final int c) {
        return (r >= 0 && r < g.length && c >= 0 && c < g[0].length);
    }
    
    private int[][] bfs(final char[][] g) {
        final int[][] dist = new int[g.length][g[0].length];
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        final boolean[][] seen = new boolean[g.length][g[0].length];
        final Pair<Integer, Integer> start = Pair.of(0,  0);
        final Pair<Integer, Integer> end = Pair.of(g.length - 1,  g[0].length - 1);
        final Deque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            final Pair<Integer, Integer> last = queue.poll();
            if (end.equals(last)) {
                return dist;
            }
            final int r = last.getOne();
            final int c = last.getTwo();
            if (seen[r][c]) {
                continue;
            }
            seen[r][c] = true;
            for (final Pair<Integer, Integer> d : NESW) {
                final int nr = r + d.getOne();
                final int nc = c + d.getTwo();
                if (!inBounds(g, nr, nc)) {
                    continue;
                }
                if (g[nr][nc] != '.') {
                    continue;
                }
                final int alt = dist[r][c];
                if (alt < dist[nr][nc]) {
                    dist[nr][nc] = alt;
                    queue.addFirst(Pair.of(nr, nc));
                }
            }
            // .***.
            // *****
            // **X**
            // *****
            // .***.
            for (int dr = -2; dr <= 2; dr++) {
                for (int dc = -2; dc <= 2; dc++) {
                    if (Math.abs(dr) + Math.abs(dc) > 3) {
                        continue;
                    }
                    final int nr = r + dr;
                    final int nc = c + dc;
                    if (!inBounds(g, nr, nc)) {
                        continue;
                    }
                    final int alt = dist[r][c] + 1;
                    if (alt < dist[nr][nc]) {
                        dist[nr][nc] = alt;
                        queue.addLast(Pair.of(nr, nc));
                    }
                }
            }
        }
        throw new IllegalStateException("Unsolvable");
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final int h = sc.nextInt();
        final int w = sc.nextInt();
        final char[][] g = new char[h][w];
        for (int j = 0; j < h; j++) {
            g[j] = sc.next().toCharArray();
        }
        final int[][] ans = bfs(g);
        this.out.println(ans[h - 1][w - 1]);
    }
    
    public void solve() {
        try (final FastScanner sc = new FastScanner(this.in)) {
            final int numberOfTestCases;
            if (sample) {
                numberOfTestCases = sc.nextInt();
            } else {
                numberOfTestCases = 1;
            }
            for (int i = 0; i < numberOfTestCases; i++) {
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
        
        @SuppressWarnings("unused")
        public int[] nextIntArray(final int n) {
            final int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = nextInt();
            }
            return a;
        }
        
        @SuppressWarnings("unused")
        public long nextLong() {
            return Long.parseLong(next());
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

    private static final class Pair<L, R> {
        private final L one;
        private final R two;

        private Pair(final L one, final R two) {
            this.one = one;
            this.two = two;
        }

        public static <L, R> Pair<L, R> of(final L one, final R two) {
            return new Pair<>(one, two);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(one, two);
        }

        @Override
        @SuppressWarnings("unchecked")
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
            final Pair<L, R> other = (Pair<L, R>) obj;
            return Objects.equals(one, other.one) && Objects.equals(two, other.two);
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Pair [one=").append(one).append(", two=").append(two).append("]");
            return builder.toString();
        }

        public L getOne() {
            return one;
        }
        
        public R getTwo() {
            return two;
        }
    }
}
