package slashee.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {
	private static final String CONFIG_FILENAME = "config.properties";
	private static final String SLACK_TOKEN_KEY = "slack.token";
	private static final String SLACK_INCLUDEDELETEDUSERS_KEY = "slack.users.includeDeletedUsers";
	private static final String SLACK_INCLUDEGUESTUSERS_KEY = "slack.users.includeGuestUsers";
	private static final String SLACK_INCLUDEHIDDENFIELDS_KEY = "slack.team.includeHiddenFields";
	private static final String SLACK_URL_KEY = "slack.url";
	private static final String SLACK_EMAILKEY_KEY = "slack.profile.emailKey";
	private static final String SLACK_ALTERNATEEMAIL_KEY = "slack.profile.alternateEmailKey";
	private static final String SHEETS_SECRET_CLIENT = "sheets.secrets.client";
	private static final String SHEETS_SECRET_USER = "sheets.secrets.user";
	private static final String SHEETS_APPLICATION_NAME = "sheets.application.name";
	private static final String SHEETS_APPLICATION_SHEETS_ID = "sheets.application.sheets.id";
	private static final String SHEETS_APPLICATION_SHEETS_RANGE = "sheets.application.sheets.range";

	public static String getSlackToken() {
		return get(SLACK_TOKEN_KEY);
	}
	
	public static String getSlackUrl() {
		return get(SLACK_URL_KEY);
	}
	
	public static String getEmailKey() {
		return get(SLACK_EMAILKEY_KEY);
	}
	
	public static String getAlternateEmailKey() {
		return get(SLACK_ALTERNATEEMAIL_KEY);
	}

	public static String getSheetsSecretClient() {
		return get(SHEETS_SECRET_CLIENT);
	}
	public static String getSheetsSecretUser() {
		return get(SHEETS_SECRET_USER);
	}
	public static String getSheetsApplicationName() {
		return get(SHEETS_APPLICATION_NAME);
	}
	public static String getSheetsApplicationSheetsId() {
		return get(SHEETS_APPLICATION_SHEETS_ID);
	}
	public static String getSheetsApplicationSheetsRange() {
		return get(SHEETS_APPLICATION_SHEETS_RANGE);
	}

	public static Boolean getIncludeDeletedUsers() {
		return getBoolean(SLACK_INCLUDEDELETEDUSERS_KEY);
	}
	
	public static Boolean getIncludeGuestUsers() {
		return getBoolean(SLACK_INCLUDEGUESTUSERS_KEY);
	}
	
	public static Boolean getIncludeHiddenFields() {
		return getBoolean(SLACK_INCLUDEHIDDENFIELDS_KEY);
	}


	
	private static Boolean getBoolean(String key) {
		String theBoolean = get(key);
		if (null == theBoolean) {
			return null;
		}
		return Boolean.valueOf(theBoolean);
	}
	
	private static String get(String key) {
		File configFile = new File(CONFIG_FILENAME);
    	
    	try {
    		FileReader reader = new FileReader(configFile);
    		Properties props = new Properties();
    		props.load(reader);
    		
    		String value = props.getProperty(key);
    		
    		if (null == value) {
    			Printer.error("Key with name '" + key +  "' was not found in file '" + CONFIG_FILENAME + "' Please add the key-value-pair to the file and try again.");
    		}
    		
    		return value;
    	} catch (FileNotFoundException ex) {
    		// file does not exist
    		Printer.error("File '" + CONFIG_FILENAME + "' was not found. Please create the file in the program's root folder and try again.");
    	} catch (IOException ex) {
    		// I/O error
    		Printer.errorIO();
    	}
    	return null;
	}
	
}
