package com.github.pareronia.atcoder.abc._438.d;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.github.pareronia.atcoder.MainTestBase;

@Timeout(2)
class MainABC438DTest extends MainTestBase<Main> {

	protected MainABC438DTest() {
		super(Main.class);
	}

	@Test
	void test() throws Throwable {
        final Random rand = new Random(System.nanoTime());
        final int n = 40_000;
        
        runWithTempFile(writer -> {
            writer.write(String.valueOf(n));
            writer.newLine();
            for (int i = 0; i < 3; i++) {
            	for (int j = 0; j < n; j++) {
            		writer.write(String.valueOf(rand.nextInt(999_999) + 1));
            		writer.write(" ");
            	}
            	writer.newLine();
			}
        });
	}
}
