package com.github.pareronia.atcoder.abc._214.c;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.github.pareronia.atcoder.MainTestBase;

class MainABC214CTestCase extends MainTestBase<Main> {

    protected MainABC214CTestCase() {
        super(Main.class);
    }

    @Test
    void test() throws Throwable {
        final Random rand = new Random(System.nanoTime());
        final int n = 200_000;
        
        final List<String> result = runWithTempFile(writer -> {
            writer.write(String.valueOf(n));
            writer.newLine();
            for (int i = 0; i < n; i++) {
                writer.write(String.valueOf(rand.nextInt(999_999_999) + 1));
                writer.write(" ");
            }
            writer.newLine();
            for (int i = 0; i < n; i++) {
                writer.write(String.valueOf(rand.nextInt(999_999_999) + 1));
                writer.write(" ");
            }
            writer.newLine();
        });
        
        assertThat(result.size(), is(n));
    }
}
