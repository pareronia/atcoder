package com.github.pareronia.atcoder.abc._245.e;

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
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * E - Wrapping Chocolate
 * @see <a href="https://atcoder.jp/contests/abc245/tasks/abc245_e">https://atcoder.jp/contests/abc245/tasks/abc245_e</a>
 */
public class Main {

    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.in = in;
        this.out = out;
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final int n = sc.nextInt();
        final int m = sc.nextInt();
        final int[] a = sc.nextIntArray(n);
        final int[] b = sc.nextIntArray(n);
        final int[] c = sc.nextIntArray(m);
        final int[] d = sc.nextIntArray(m);
        final PriorityQueue<int[]> q = new PriorityQueue<>(comparator());
        for (int j = 0; j < n; j++) {
            q.add(new int[] { 0, a[j], b[j] });
        }
        for (int j = 0; j < m; j++) {
            q.add(new int[] { 1, c[j], d[j] });
        }
        final TreeMap<Integer, Integer> s = new TreeMap<>();
        while (!q.isEmpty()) {
            final int[] x = q.peek();
            if (x[0] == 1) {
                s.merge(x[2], 1, Integer::sum);
            } else {
                final Integer y = s.ceilingKey(x[2]);
                if (y == null) {
                    break;
                }
                if (s.get(y) == 1) {
                    s.remove(y);
                } else {
                    s.merge(y, -1, Integer::sum);
                }
            }
            q.remove();
        }
        final String ans = q.isEmpty() ? "Yes" : "No";
        this.out.println(ans);
    }

    private Comparator<? super int[]> comparator() {
        return (x1, x2) -> {
            final int c = Integer.compare(x2[1], x1[1]);
            if (c == 0) {
                return Integer.compare(x2[0], x1[0]);
            }
            return c;
        };
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
        
        public int[] nextIntArray(final int n) {
            final int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = nextInt();
            }
            return a;
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
