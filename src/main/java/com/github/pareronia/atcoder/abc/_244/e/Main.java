package com.github.pareronia.atcoder.abc._244.e;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * E - King Bombee
 * @see <a href="https://atcoder.jp/contests/abc244/tasks/abc244_e">https://atcoder.jp/contests/abc244/tasks/abc244_e</a>
 */
public class Main {
    
    private static final int MOD = 998244353;

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
        final int k = sc.nextInt();
        final int s = sc.nextInt();
        final int t = sc.nextInt();
        final int x = sc.nextInt();
        final Map<Integer, Set<Integer>> adj = new HashMap<>();
        for (int j = 0; j < m; j++) {
            final int v1 = sc.nextInt();
            final int v2 = sc.nextInt();
            if (!adj.containsKey(v1)) {
                adj.put(v1, new HashSet<>());
            }
            if (!adj.containsKey(v2)) {
                adj.put(v2, new HashSet<>());
            }
            adj.get(v1).add(v2);
            adj.get(v2).add(v1);
        }
        final int[][][] dp = new int[k + 1][n + 1][2];
        for (int j = 0; j <= n; j++) {
            dp[0][j][0] = j == s ? 1 : 0;
            dp[0][j][1] = 0;
        }
        for (int j1 = 1; j1 <= k; j1++) {
            for (int j2 = 1; j2 <= n; j2++) {
                for (int j3 = 0; j3 < 2; j3++) {
                    for (final int nb : adj.getOrDefault(j2, emptySet())) {
                        dp[j1][j2][j3] += dp[j1 - 1][nb][j2 == x ? 1 - j3 : j3];
                        dp[j1][j2][j3] %= MOD;
                    }
                }
            }
        }
        final int ans = dp[k][t][0];
        this.out.println(ans);
    }
    
    public void solve() {
        try (final FastScanner sc = new FastScanner(this.in)) {
            final int numberOfTestCases;
            if (isSample()) {
                numberOfTestCases = sc.nextInt();
            } else {
                numberOfTestCases = 1;
            }
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
