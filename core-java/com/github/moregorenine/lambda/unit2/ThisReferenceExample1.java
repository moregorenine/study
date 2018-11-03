package com.github.moregorenine.lambda.unit2;

public class ThisReferenceExample1 {
	public static void main(String[] args) {
		ThisReferenceExample1 thisReferenceExample = new ThisReferenceExample1();

		thisReferenceExample.doProcess(10, new Process() {
			@Override
			public void process(int i) {
				System.out.println("value of i is " + i);
				System.out.println(this);
			}
		});
	}

	private void doProcess(int i, Process p) {
		p.process(i);
	}
}
