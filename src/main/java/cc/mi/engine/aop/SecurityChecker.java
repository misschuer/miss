package cc.mi.engine.aop;

public class SecurityChecker {

	private SecurityChecker() {
	}

	public static void checkSecurity() {
		System.out.println("checkSecurity");
	}
	
	public static void after() {
		System.out.println("after");
	}
}
