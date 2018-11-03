package com.github.moregorenine.lambda.unit2;

public class ThisReferenceExample3 {
	public static void main(String[] args) {
		ThisReferenceExample3 thisReferenceExample = new ThisReferenceExample3();

		thisReferenceExample.doProcess(10, i -> {
			System.out.println("value of i is " + i);
//			System.out.println(this); this will not work
		});
		
		thisReferenceExample.execute();
	}

	private void doProcess(int i, Process p) {
		p.process(i);
	}
	
	private void execute() {
		doProcess(10, i -> {
			System.out.println("value of i is " + i);
			System.out.println(this);
		});
	}
}
