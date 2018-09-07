package slashee.main;

public class Printer {
	
	private static void errorSpecific(String exceptionName) {
		error(exceptionName + " Exception occured. Please retry. If it still fails, contact the developer :-)");
	}
	
	public static void error(String msg) {
		System.err.println(msg);
	}
	
	public static void errorIO() {
		errorSpecific("I/O");
	}
	
	public static void errorSlackApi() {
		errorSpecific("Slack API");
	}
	
	public static void info(String msg) {
		System.out.println(msg);
	}
}
