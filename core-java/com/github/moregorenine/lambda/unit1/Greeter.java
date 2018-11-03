package com.github.moregorenine.lambda.unit1;

public class Greeter {

	public static void main(String[] args) {
		/*interface를 구현하는 람다*/
//		MyLambda interface를 별도의 구현하는 class생성할 필요 없이 inline 선언으로 대체 가능
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
