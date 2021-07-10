package com.github.pareronia.atcoder.abc._208.D;

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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    private final boolean sample;
    private final InputStream in;
    private final PrintStream out;
    
    private Main(final Boolean sample, final InputStream in, final PrintStream out) {
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
    
private Result<?> handleTestCase(final Scanner sc, final Integer c) {
        final int n = sc.nextInt();
        final int m = sc.nextInt();
        final int[][] dist = new int[n][n];
        final int inf = Integer.MAX_VALUE / 2;
        for (int j = 0; j < n; j++) {
            Arrays.fill(dist[j], inf);
            dist[j][j] = 0;
        }
        for (int j = 0; j < m; j++) {
            final int u = sc.nextInt() - 1;
            final int v = sc.nextInt() - 1;
            dist[u][v] = sc.nextInt();
        }
        long ans = 0;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    final int dist_ikj = dist[i][k] + dist[k][j];
                    dist[i][j] = Math.min(dist[i][j], dist_ikj);
                    if (dist[i][j] != inf) {
                        ans += dist[i][j];
                    }
                }
            }
        }
        return new Result<>(c, List.of(ans));
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
        if (sample) {
            is = Main.class.getResourceAsStream("sample.in");
            out = new PrintStream(baos, true);
        } else {
            is = System.in;
            out = System.out;
        }
        
        new Main(sample, is, out).solve();
    	
        if (sample) {
            final Path path
                    = Paths.get(Main.class.getResource("sample.out").toURI());
            final List<String> expected = Files.readAllLines(path);
            final List<String> actual = asList(baos.toString().split("\\r?\\n"));
            if (!expected.equals(actual)) {
                throw new AssertionError(String.format(
                        "Expected %s, got %s", expected, actual));
            }
            actual.forEach(System.out::println);
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
