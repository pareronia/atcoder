package com.github.pareronia.atcoder.abc._211.c;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * C - chokudai
 * @see <a href="https://atcoder.jp/contests/abc211/tasks/abc211_c">https://atcoder.jp/contests/abc211/tasks/abc211_c</a>
 */
public class Main {

    private static final long MOD = 1_000_000_007L;

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
    
    private Result<?> handleTestCase(final Scanner sc, final Integer i) {
        final String s = sc.next();
        final Map<Pair<Integer, Integer>, Long> map = new HashMap<>();
        int prevMapSize = 0;
        for (int j = s.length() - 1; j >= 0; j--) {
            if (s.charAt(j) == 'i') {
                map.put(Pair.of(1, j), 1L);
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 2; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'a') {
                map.put(Pair.of(2, j), dp(map, 1, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 3; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'd') {
                map.put(Pair.of(3, j), dp(map, 2, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 4; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'u') {
                map.put(Pair.of(4, j), dp(map, 3, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 5; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'k') {
                map.put(Pair.of(5, j), dp(map, 4, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 6; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'o') {
                map.put(Pair.of(6, j), dp(map, 5, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 7; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'h') {
                map.put(Pair.of(7, j), dp(map, 6, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        prevMapSize = map.size();
        for (int j = s.length() - 8; j >= 0; j--) {
            final int jj = j;
            if (s.charAt(j) == 'c') {
                map.put(Pair.of(8, j), dp(map, 7, jj));
            }
        }
        if (map.size() == prevMapSize) {
            return new Result<>(i, List.of(0));
        }
        final long ans = map.entrySet().stream()
                .filter(e -> e.getKey().getOne() == 8)
                .map(Entry::getValue)
                .collect(summingLong(Long::valueOf));
        return new Result<>(i, List.of(ans % MOD));
    }

    private long dp(final Map<Pair<Integer, Integer>, Long> map, final int size, final int jj) {
        return map.entrySet().stream()
                .filter(e -> e.getKey().getOne() == size && e.getKey().getTwo() > jj)
                .map(Entry::getValue)
                .collect(summingLong(Long::valueOf));
    }
    
    private void output(final List<Result<?>> results) {
        results.forEach(r -> this.out.println(
                r.values.stream().map(Object::toString).collect(joining(" "))));
    }
    
    public void solve() throws IOException {
        try (final Scanner sc = new Scanner(new InputStreamReader(this.in))) {
            final int numberOfTestCases;
            if (sample) {
                numberOfTestCases = sc.nextInt();
            } else {
                numberOfTestCases = 1;
            }
            final List<Result<?>> results =
                    Stream.iterate(1, i -> i <= numberOfTestCases, i -> i + 1)
                            .map(i -> handleTestCase(sc, i))
                            .collect(toList());
            output(results);
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
        return "sample".equals(System.getProperty("atcoder"));
    }
    
    private static final class Result<T> {
        private final List<T> values;

        public Result(final int caseNumber, final List<T> values) {
            this.values = values;
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

        public L getOne() {
            return one;
        }
        
        public R getTwo() {
            return two;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Pair [one=").append(one).append(", two=").append(two).append("]");
            return builder.toString();
        }
    }
}
