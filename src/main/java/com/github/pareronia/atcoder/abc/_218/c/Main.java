package com.github.pareronia.atcoder.abc._218.c;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import java.util.stream.StreamSupport;

/**
 * C - Shapes
 * @see <a href="https://atcoder.jp/contests/abc218/tasks/abc218_c">https://atcoder.jp/contests/abc218/tasks/abc218_c</a>
 */
public class Main {

    private static final char ON = '#';
    
    private final InputStream in;
    private final PrintStream out;
    
    public Main(
            final Boolean sample, final InputStream in, final PrintStream out) {
        this.in = in;
        this.out = out;
    }
    
    private void handleTestCase(final FastScanner sc, final Integer i) {
        final int n = sc.nextInt();
        final char[][] s = new char[n][n];
        for (int j = 0; j < n; j++) {
            s[j] = sc.next().toCharArray();
        }
        final Grid gs = new Grid(s);
        final char[][] t = new char[n][n];
        for (int j = 0; j < n; j++) {
            t[j] = sc.next().toCharArray();
        }
        Grid gt = new Grid(t);
        String ans = "No";
        if (gs.countAllEqualTo(ON) == gt.countAllEqualTo(ON)) {
            for (int j = 0; j < 4; j++) {
                gt = gt.rotate();
                if (gt.sameAs(gs)) {
                    ans = "Yes";
                    break;
                }
            }
        }
        this.out.println(ans);
    }
    
    public void solve() {
        try (final FastScanner sc = new FastScanner(this.in)) {
            final int numberOfTestCases;
            if (isSample()) {
                numberOfTestCases = sc.nextInt();
            } else {
                numberOfTestCases = 1;
            }
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
    private static class Grid {
        private final char[][] cells;

        public Grid(final char[][] cells) {
            this.cells = cells;
        }

        private Integer getHeight() {
            return this.cells.length;
        }

        private Integer getWidth() {
            return this.cells[0].length;
        }
        
        private Integer getMaxRowIndex() {
            return getHeight() - 1;
        }
        
        private char[] getRow(final Integer row) {
            return Arrays.copyOf(this.cells[row], getWidth());
        }
        
        private String getRowAsString(final Integer row) {
            return new String(getRow(row));
        }
	
        private Stream<Cell> findAllMatching(final Predicate<Character> test) {
            final Builder<Cell> builder = Stream.builder();
            for (int row = 0; row < getHeight(); row++) {
                final char[] cs = cells[row];
                for (int col = 0; col < getWidth(); col++) {
                    if (test.test(cs[col])) {
                        builder.add(Cell.at(row, col));
                    }
                }
            }
            return builder.build();
        }
        
        private Stream<Cell> getAllEqualTo(final char ch) {
            return findAllMatching(c -> c == ch);
        }
        
        private long countAllEqualTo(final char ch) {
            return getAllEqualTo(ch).count();
        }
        
        public Grid rotate() {
            final char[][] cells = new char[getWidth()][];
            for (int col = 0; col < getWidth(); col++) {
                final char[] newRow = new char[getHeight()];
                for (int row = getMaxRowIndex(); row >= 0; row--) {
                    newRow[getMaxRowIndex() - row] = this.cells[row][col];
                }
                cells[col] = newRow;
            }
            return new Grid(cells);
        }
        
        private Iterable<String> getRowsAsStrings() {
            return () -> new Iterator<>() {
                int i = 0;
                
                @Override
                public boolean hasNext() {
                    return i <= Grid.this.getMaxRowIndex();
                }

                @Override
                public String next() {
                    return Grid.this.getRowAsString(i++);
                }
            };
        }
        
        @Override
        public String toString() {
            return StreamSupport.stream(getRowsAsStrings().spliterator(), false)
                    .collect(joining(System.lineSeparator()));
        }
        
        private Cell getTopLeft() {
            return this.getAllEqualTo(ON).findFirst().orElseThrow();
        }
        
        public boolean sameAs(final Grid other) {
            final Cell tl1 = this.getTopLeft();
            final Cell tl2 = other.getTopLeft();
            final int dr = tl2.row - tl1.row;
            final int dc = tl2.col - tl1.col;
            for (int r = 0; r < getHeight(); r++) {
                for (int c = 0; c < getWidth(); c++) {
                    final int rr = r + dr;
                    final int cc = c + dc;
                    if (0 <= rr && rr < getHeight() && 0 <= cc && cc  < getWidth()) {
                        if (this.cells[r][c] != other.cells[rr][cc]) {
                            return false;
                        }
                    } else {
                        if (this.cells[r][c] == ON) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
    
	private static final class Cell {
		private final Integer row;
		private final Integer col;
		
		private Cell(final Integer row, final Integer col) {
            this.row = row;
            this.col = col;
        }

        public static Cell at(final Integer row, final Integer col) {
			return new Cell(row, col);
		}
	}
}
