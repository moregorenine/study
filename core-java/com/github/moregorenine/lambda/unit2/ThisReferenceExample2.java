package com.github.moregorenine.lambda.unit2;

public class ThisReferenceExample2 {
	public static void main(String[] args) {
		ThisReferenceExample2 thisReferenceExample = new ThisReferenceExample2();

		thisReferenceExample.doProcess(10, i -> {
			System.out.println("value of i is " + i);
//			System.out.println(this);
		});
	}

	private void doProcess(int i, Process p) {
		p.process(i);
	}
}
