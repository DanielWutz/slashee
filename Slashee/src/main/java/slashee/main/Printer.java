package slashee.main;

public class Printer {
	
	private static void errorSpecific(String exceptionName) {
		error(exceptionName + " Exception occured. Possibly, you are offline. " +
				"Please check your internet connection and retry. "
				+ "If it still fails, contact the developers :-)");
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
