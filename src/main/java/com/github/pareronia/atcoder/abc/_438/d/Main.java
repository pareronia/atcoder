package com.github.pareronia.atcoder.abc._438.d;

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
import java.util.List;
import java.util.StringTokenizer;

/**
 * D - Tail of Snake
 * @see <a href="https://atcoder.jp/contests/abc438/tasks/abc438_d">https://atcoder.jp/contests/abc438/tasks/abc438_d</a>
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
    	final long[][] a = new long[3][n];
    	for (int j = 0; j < 3; j++) {
			a[j] = sc.nextLongArray(n);
		}
    	final DFS dfs = new DFS(a);
		this.out.println(dfs.dfs(2, n - 1));
    }

    private static class DFS {
    	private final long[][] cache;
    	private final long[][] a;

		private DFS(final long[][] a) {
			this.a = a;
			this.cache = new long[a.length][a[0].length];
		}
    	
		public long dfs(final int i, final int j) {
			if (this.cache[i][j] > 0) {
				return this.cache[i][j];
			}
			long ans = 0;
			if (i == 0) {
				for (int k = 0; k <= j; k++) {
					ans += a[i][k];
				}
			} else {
				long ak = a[i][j];
				for (int k = j; k >= i; k--) {
					ans = Math.max(ans, ak + dfs(i - 1, k - 1));
					ak += a[i][k - 1];
				}
			}
			this.cache[i][j] = ans;
			return ans;
		}
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
        
        public long[] nextLongArray(final int n) {
            final long[] a = new long[n];
            for (int j = 0; j < n; j++) {
                a[j] = nextLong();
            }
            return a;
        }
        
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
