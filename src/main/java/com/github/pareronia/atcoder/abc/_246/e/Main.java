package com.github.pareronia.atcoder.abc._246.e;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/**
 * E - Bishop 2
 * @see <a href="https://atcoder.jp/contests/abc246/tasks/abc246_e">https://atcoder.jp/contests/abc246/tasks/abc246_e</a>
 */
public class Main {

    private static final int[][] DIAG = new int[][] {
        new int[] { -1, 1 },
        new int[] { 1, 1 },
        new int[] { 1, -1 },
        new int[] { -1, -1 }
    };
    
    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.in = in;
        this.out = out;
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final int n = sc.nextInt();
        final int[] start = new int[] { sc.nextInt() - 1, sc.nextInt() - 1 };
        final int[] end = new int[] { sc.nextInt() - 1, sc.nextInt() - 1 };
        final char[][] g = new char[n][n];
        for (int j = 0; j < n; j++) {
            g[j] = sc.next().toCharArray();
        }
        final Deque<int[]> q = new ArrayDeque<>();
        final int[][][] dist = new int[n][n][4];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                Arrays.fill(dist[j][k], Integer.MAX_VALUE);
            }
        }
        for (final int[] d : neighbours(g, start[0], start[1], n)) {
            final int rr = d[0];
            final int cc = d[1];
            q.addLast(new int[] { rr, cc, d[2] });
            dist[rr][cc][d[2]] = 1;
        }
        final boolean[][][] seen = new boolean[n][n][4];
        int ans = -1;
        while (!q.isEmpty()) {
            final int[] curr = q.poll();
            final int r = curr[0];
            final int c = curr[1];
            if (seen[r][c][curr[2]]) {
                continue;
            }
            seen[r][c][curr[2]] = true;
            final int cdist = dist[r][c][curr[2]];
            if (r == end[0] && c == end[1]) {
                ans = cdist;
                break;
            }
            for (final int[] d : neighbours(g, r, c, n)) {
                final int rr = d[0];
                final int cc = d[1];
                final int dd = curr[2] == d[2] ? 0 : 1;
                final int ndist = cdist + dd;
                final int[] next = new int[] { rr, cc, d[2] };
                if (dist[rr][cc][d[2]] > ndist) {
                    dist[rr][cc][d[2]] = ndist;
                    if (dd == 0) {
                        q.addFirst(next);
                    } else {
                        q.addLast(next);
                    }
                }
            }
        }
        this.out.println(ans);
    }
    
    private List<int[]> neighbours(final char[][]g, final int r, final int c, final int n) {
        final ArrayList<int[]> nb = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final int rr = r + DIAG[i][0];
            final int cc = c + DIAG[i][1];
            if (rr < 0 || rr >= n || cc < 0 || cc >= n || g[rr][cc] == '#') {
                continue;
            }
            nb.add(new int[] { rr, cc, i });
        }
        return nb;
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
}
