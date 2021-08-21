package com.github.pareronia.atcoder.abc._215.c;

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
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * C - One More aab aba baa
 * @see <a href="https://atcoder.jp/contests/abc215/tasks/abc215_c">https://atcoder.jp/contests/abc215/tasks/abc215_c</a>
 */
public class Main {

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
    
    private static final class Heap {
        
        public static List<String> list(final String s) {
            final List<String> list = new ArrayList<>();
            accept(s, list::add);
            return list;
        }
        
        public static void accept(final String s, final Consumer<String> consumer) {
            final char[] ch = s.toCharArray();
            final int[] a = new int[ch.length];
            for (int j = 0; j < a.length; j++) {
                a[j] = j;
            }
            accept(a, t -> {
                final String ss = Arrays.stream(t).mapToObj(i -> ch[i])
                    .collect(Collector.of(StringBuilder::new,
                                          StringBuilder::append,
                                          StringBuilder::append,
                                          StringBuilder::toString));
                consumer.accept(ss);
            });
        }

        public static void accept(final int[] a, final Consumer<int[]> consumer) {
            heaps_algorithm(a, a.length, consumer);
        }
        
        private static void heaps_algorithm(
                final int[] a,
                final int n,
                final Consumer<int[]> consumer
        ) {
            if (n == 1) {
                // (got a new permutation)
//                System.out.println(Arrays.toString(a));
                consumer.accept(a);
                return;
            }
            for (int i = 0; i < n - 1; i++) {
                heaps_algorithm(a, n - 1, consumer);
                // always swap the first when odd,
                // swap the i-th when even
                if (n % 2 == 0) {
                    swap(a, n - 1, i);
                } else {
                    swap(a, n - 1, 0);
                }
            }
            heaps_algorithm(a, n - 1, consumer);
        }
        
        private static void swap(final int[] a, final int i, final int j) {
            final int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final String s = sc.next();
        final int k = sc.nextInt();
        final TreeSet<String> set = new TreeSet<>();
        Heap.accept(s, set::add);
        final String ans = set.stream().skip(k - 1).findFirst().get();
        this.out.println(ans);
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
}
