package com.github.pareronia.atcoder.abc._436.d;

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
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * D - Teleport Maze
 * @see <a href="https://atcoder.jp/contests/abc436/tasks/abc436_d">https://atcoder.jp/contests/abc436/tasks/abc436_d</a>
 */
public class Main {

    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.in = in;
        this.out = out;
    }
    
    @SuppressWarnings("unchecked")
	private void handleTestCase(final FastScanner sc, final Integer i) {
    	final int h = sc.nextInt();
    	final int w = sc.nextInt();
    	final Map<Integer, Integer> cells = new HashMap<>();
    	final Set<Integer>[] teleports = new Set[26];
    	for (int r = 1; r <= h; r++) {
    		final String s = sc.next();
    		for (int c = 1; c <= w; c++) {
    			final Integer cell = 10000 * r + c;
    			final int ch = s.charAt(c - 1);
				if (ch == 46) {
    				cells.put(cell, ch);
    			} else if (97 <= ch && ch <= 122) {
    				if (teleports[ch - 97] == null) {
    					teleports[ch - 97] = new HashSet<>();
    				}
    				teleports[ch - 97].add(cell);
    				cells.put(cell, ch - 97);
    			}
			}
		}
    	final int start = 10001;
    	final Deque<State> q = new ArrayDeque<>();
    	q.add(new State(start, 0));
    	final Set<Integer> seen = new HashSet<>();
    	seen.add(start);
    	while (!q.isEmpty()) {
    		final State state = q.pollFirst();
    		if (state.cell == h * 10000 + w) {
    			this.out.println(state.distance);
    			return;
    		}
			final int r = state.cell / 10000;
			final int c = state.cell % 10000;
			for (final Direction d : Direction.ALL) {
				final int nxt = (r + d.dr) * 10000 + (c + d.dc);
				if (!cells.containsKey(nxt) || seen.contains(nxt)) {
					continue;
				}
				seen.add(nxt);
				q.add(new State(nxt, state.distance + 1));
			}
			if (!cells.containsKey(state.cell)) {
				continue;
			}
    		final int v = cells.get(state.cell);
			if (v != 46) {
    			for (final int t : teleports[v]) {
    				cells.remove(t);
    				seen.add(t);
    				q.add(new State(t, state.distance + 1));
    			}
    		}
    	}
    	this.out.println(-1);
    }
    
    record Direction(int dr, int dc) {
    	static Direction[] ALL = {
    			new Direction(-1, 0), new Direction(1, 0),
    			new Direction(0, 1), new Direction(0, -1)};
    }

    record State(int cell, int distance) {}

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
