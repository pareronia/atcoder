package com.github.pareronia.atcoder.practice.B;

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
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    private final boolean sample;
    private final InputStream in;
    private final PrintStream out;
    
    private Main(
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
    
    private void mergeSort(final char[] a, final char[] b, final int n,
                    final BiFunction<Character, Character, Boolean> question) {
        for (int width = 1; width < n; width *= 2)  {
            for (int i = 0; i < n; i = i + 2 * width) {
                final int left = i;
                final int right = Math.min(i + width, n);
                final int end = Math.min(i + 2 * width, n);
                int m = left;
                int j = right;
                for (int k = left; k < end; k++) {
                    if (m < right && (j >= end || question.apply(a[m], a[j]))) {
                        b[k] = a[m];
                        m++;
                    } else {
                        b[k] = a[j];
                        j++;
                    }
                }
            }
            System.arraycopy(b, 0, a, 0, n);
        }
    }
    
    private Result<?> handleTestCase(final Scanner sc, final Integer i) {
        final int n = sc.nextInt();
        sc.nextInt(); // q
        final char[] ch = new char[n];
        for (int j = 0; j < n; j++) {
            ch[j] = (char) ('A' + j);
        }
        final char[] sorted = new char[n];
        mergeSort(ch, sorted, n, (a, b) -> {
            final String ans = askQuestion(String.format("? %s %s", a, b), sc);
            return "<".equals(ans);
        });

        return new Result<>(i, List.of(String.valueOf(ch)));
    }
    
    private String askQuestion(final String question, final Scanner sc) {
        this.out.println(question);
        return sc.next();
    }
    
    private void outputResult(final List<Result<?>> results) {
        results.forEach(r -> this.out.println("! " +
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
            outputResult(results);
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
