package com.github.moregorenine.lambda.unit1;

public class RunnableExample {

	public static void main(String[] args) {
		// Thread 생성
		Thread myThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Print inside Runnable");
			}
		});

		// Thread 실행
		myThread.start();

		// 람다식을 사용한 Runnable로 Thread 생성
		Thread myLambdaThread = new Thread(() -> System.out.println("Print inside Runnable"));

		// Thread 실행
		myLambdaThread.start();
	}

}
