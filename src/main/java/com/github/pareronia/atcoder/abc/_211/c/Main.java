package com.github.pareronia.atcoder.abc._211.c;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
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
import java.util.List;
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
        final char[] ch = "chokudai".toCharArray();
        final int[] dp = new int[ch.length];
        for (int j = 0; j < s.length(); j++) {
            for (int k = 0; k < ch.length; k++) {
                if (s.charAt(j) == ch[k]) {
                    final int prev = k == 0 ? 1 : dp[k - 1];
                    dp[k] = (int) ((dp[k] + prev) % MOD);
                }
            }
        }
        final int ans = dp[ch.length - 1];
        return new Result<>(i, List.of(ans));
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
}
