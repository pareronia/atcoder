package com.github.pareronia.atcoder.abc._209.D;

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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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
    
    private int[] colorTowns(final ArrayList<Integer>[] roads) {
        final Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        final int[] colors = new int[roads.length];
        Arrays.fill(colors, -1);
        colors[0] = 0;
        while (!queue.isEmpty()) {
            final Integer t = queue.poll();
            for (final Integer n : roads[t]) {
                if (colors[n] == -1) {
                    colors[n] = 1 - colors[t];
                    queue.add(n);
                }
            }
        }
        return colors;
    }
    
    @SuppressWarnings("unchecked")
    private Result<?> handleTestCase(final Scanner sc, final Integer i) {
        final int n = sc.nextInt();
        final int q = sc.nextInt();
        final ArrayList<Integer>[] roads = new ArrayList[n];
        for (int j = 0; j < n; j++) {
            roads[j] = new ArrayList<>();
        }
        for (int j = 0; j < n - 1; j++) {
            final int a = sc.nextInt();
            final int b = sc.nextInt();
            roads[a - 1].add(b - 1);
            roads[b - 1].add(a - 1);
        }
        final int[] colors = colorTowns(roads);
        final List<String> ans = new ArrayList<>();
        for (int j = 0; j < q; j++) {
            final int c = sc.nextInt();
            final int d = sc.nextInt();
            if (colors[c - 1] == colors[d - 1]) {
                ans.add("Town");
            } else {
                ans.add("Road");
            }
        }
        return new Result<>(
                i,
                List.of(ans.stream().collect(joining(System.lineSeparator()))));
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
