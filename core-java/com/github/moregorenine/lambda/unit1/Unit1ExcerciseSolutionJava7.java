package com.github.moregorenine.lambda.unit1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Unit1ExcerciseSolutionJava7 {
	public static void main(String[] args) {
		List<Person> people = Arrays.asList(new Person("길동", "홍", 45), new Person("꺽정", "임", 30),
				new Person("보고", "장", 37), new Person("철수", "김", 12), new Person("영이", "김", 12));

		System.out.println("Step1. lastname으로 정렬하라.");
		Collections.sort(people, new Comparator<Person>() {
			@Override
			public int compare(Person p1, Person p2) {
				return p1.getLastName().compareTo(p2.getLastName());
			}
		});

		System.out.println("Step2. list의 Person Objects 출력하라.");
		printAll(people);

		System.out.println("Step3. '김'씨성의 Persons 출력하라.");
		printConditionally(people, new Condition() {
			@Override
			public boolean test(Person p) {
				return p.getLastName().startsWith("김");
			}
		});

		System.out.println("Step4. 이름이 '길'로 시작하는 Persons 출력하라.");
		printConditionally(people, new Condition() {
			@Override
			public boolean test(Person p) {
				return p.getFirstName().startsWith("길");
			}
		});
	}

	/**
	 * people들 정보를 모두 출력한다.
	 * 
	 * @param people
	 */
	private static void printAll(List<Person> people) {
		for (Person p : people) {
			System.out.println(p.toString());
		}
	}

	/**
	 * 조건
	 */
	interface Condition {
		boolean test(Person p);
	}

	/**
	 * people들 정보중 condition 조건에 해당하는 Person만 출력됨
	 * 
	 * @param people
	 * @param condition
	 */
	private static void printConditionally(List<Person> people, Condition condition) {
		for (Person p : people) {
			if (condition.test(p)) {
				System.out.println(p.toString());
			}
		}
	}
}
