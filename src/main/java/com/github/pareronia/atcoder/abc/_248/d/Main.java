package com.github.pareronia.atcoder.abc._248.d;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * D - Range Count Query
 * @see <a href="https://atcoder.jp/contests/abc248/tasks/abc248_d">https://atcoder.jp/contests/abc248/tasks/abc248_d</a>
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
        final Map<Integer, List<Integer>> map = new HashMap<>();
        for (int j = 0; j < n; j++) {
            final int a = sc.nextInt();
            if (!map.containsKey(a)) {
                map.put(a, new ArrayList<>());
            }
            map.get(a).add(j);
        }
        final int q = sc.nextInt();
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < q; j++) {
            final int l = sc.nextInt() - 1;
            final int r = sc.nextInt() - 1;
            final int x = sc.nextInt();
            int ans = 0;
            if (map.containsKey(x)) {
                final List<Integer> aa = map.get(x);
                ans = upperBound(aa, r) - lowerBound(aa, l);
            }
            sb.append(ans).append(System.lineSeparator());
        }
        this.out.print(sb.toString());
    }
    
    private int lowerBound(final List<Integer> array, final int target) {
        int start = -1;
        int end = array.size();
        while (end - start > 1) {
            final int mid = (end + start) / 2;
            if (array.get(mid) >= target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return end;
    }
    
    private int upperBound(final List<Integer> array, final int target) {
        int start = -1;
        int end = array.size();
        while (end - start > 1) {
            final int mid = (end + start) / 2;
            if (array.get(mid) > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return end;
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
