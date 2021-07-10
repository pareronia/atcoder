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
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
    
    private void addRoad(final Map<Integer, Set<Integer>> roads, final int a, final int b) {
        roads.merge(
                a,
                new HashSet<>(Set.of(b)),
                (v1, v2) -> {
                    v1.addAll(v2);
                    return v1;
                });
    }
    
    private List<Integer> findShortestPath(final Map<Integer, Set<Integer>> roads, final int from, final int to) {
        final Deque<List<Integer>> queue = new ArrayDeque<>();
        queue.add(new ArrayList<>(List.of(from)));
        final Set<Integer> seen = new HashSet<>();
        List<Integer> t = null;
        while (!queue.isEmpty()) {
            t = queue.poll();
            if (seen.contains(t.get(0))) {
                continue;
            }
            if (t.get(0) == to) {
                break;
            }
            seen.add(t.get(0));
            for (final Integer n : roads.get(t.get(0))) {
                if (!seen.contains(n)) {
                    final List<Integer> l = new ArrayList<>(t);
                    l.add(0, n);
                    queue.add(l);
                }
            }
        }
        return t;
    }
    
    private Result<?> handleTestCase(final Scanner sc, final Integer i) {
        final int n = sc.nextInt();
        final int q = sc.nextInt();
        final Map<Integer, Set<Integer>> roads = new HashMap<>();
        for (int j = 0; j < n - 1; j++) {
            final int a = sc.nextInt();
            final int b = sc.nextInt();
            addRoad(roads, a, b);
            addRoad(roads, b, a);
        }
        log(() -> roads);
        final List<String> ans = new ArrayList<>();
        for (int j = 0; j < q; j++) {
            final int c = sc.nextInt();
            final int d = sc.nextInt();
            final List<Integer> path = findShortestPath(roads, c, d);
            log(() -> c + "," + d + ": " + path);
            ans.add(path.size() % 2 == 1 ? "Town" : "Road");
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
    
    private static final class Node {

        
    }
}
