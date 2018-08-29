package slashee.main;

public class Printer {
	
	private static void errorSpecific(String exceptionName, String methodname) {
		error(exceptionName + " Exception occured while calling " + methodname + ". Please retry. If it still fails, contact the developer :-)");
	}
	
	
	public static void error(String msg) {
		System.err.println(msg);
	}
	
	public static void errorIO(String methodname) {
		errorSpecific("I/O", methodname);
	}
	
	public static void errorSlackApi(String methodname) {
		errorSpecific("Slack API", methodname);
	}
	
	public static void info(String msg) {
		System.out.println(msg);
	}
}
