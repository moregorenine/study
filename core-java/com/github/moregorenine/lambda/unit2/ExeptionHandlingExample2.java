package com.github.moregorenine.lambda.unit2;

import java.util.function.BiConsumer;

public class ExeptionHandlingExample2 {

	public static void main(String[] args) {
		int[] sumeNumbers = { 1, 2, 3, 4, 5 };
		int key = 0;

		process(sumeNumbers, key, (v, k) -> {
			try {
				System.out.println(v / k);
			} catch (ArithmeticException e) {
				System.out.println("ArithmeticException 발생...");
			}
		});
	}

	private static void process(int[] sumeNumbers, int key, BiConsumer<Integer, Integer> consumer) {
		for (int i : sumeNumbers) {
			consumer.accept(i, key);
		}
	}
}
