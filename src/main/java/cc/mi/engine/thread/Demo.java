package cc.mi.engine.thread;

import java.util.Scanner;

public class Demo {
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		String a = cin.next();
		String b = cin.next();
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		cin.close();
	}
}