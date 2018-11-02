package com.github.moregorenine.lambda;

public class Greeter {

	public static void main(String[] args) {
		MyLambda myLambda = () -> System.out.println("Hello World!!");
		myLambda.foo();

		MyAdd myAdd = (int a, int b) -> a + b;
		int resultAdd = myAdd.add(10, 20);
		System.out.println(resultAdd);

	}

}

interface MyLambda {
	void foo();
}

interface MyAdd {
	int add(int x, int y);
}
