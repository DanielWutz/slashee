package slashee.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.model.User.Profile.Field;

public class SlackHandler {
	
	private static final String CONFIG_FILENAME = "config.properties";
	private static final String SLACK_TOKEN_KEY = "slack.token";
    
	public static String getSlackToken() {
		File configFile = new File(CONFIG_FILENAME);
    	
    	try {
    		FileReader reader = new FileReader(configFile);
    		Properties props = new Properties();
    		props.load(reader);
    		
    		String slackToken = props.getProperty(SLACK_TOKEN_KEY);
    		
    		if (null == slackToken) {
    			Printer.error("Key with name '" + SLACK_TOKEN_KEY +  "' was not found in file '" + CONFIG_FILENAME + "' Please add the key-value-pair to the file and try again.");
    		}
    		
    		return slackToken;
    	} catch (FileNotFoundException ex) {
    		// file does not exist
    		Printer.error("File '" + CONFIG_FILENAME + "' was not found. Please create the file in the program's root folder and try again.");
    	} catch (IOException ex) {
    		// I/O error
    		Printer.errorIO("getSlackToken()");
    	}
    	return null;
	}
	
    public static void main(String[] args) {
    	final String methodname = "main(String[] args)";
    	
    	String slackToken = getSlackToken();
    	Slack slack = Slack.getInstance();
    	try {
			UsersListResponse usersList = slack.methods().usersList(UsersListRequest.builder().token(slackToken).build());
			for (User user : usersList.getMembers()) {
					Printer.info(user.getProfile().getDisplayName());
					Printer.info("");
					Map<String, Field> fields = user.getProfile().getFields();
					if (fields != null && fields.entrySet() != null) {
						for (Map.Entry<String, Field> entry : fields.entrySet()) {
						   Printer.info(entry.getKey() + " / " + entry.getValue());
						}
					}
					Printer.info("====================================");
			}
		} catch (IOException e) {
			Printer.errorIO(methodname);
		} catch (SlackApiException e) {
			Printer.errorSlackApi(methodname);
		}
    }
}
