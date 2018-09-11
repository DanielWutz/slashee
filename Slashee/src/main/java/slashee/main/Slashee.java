package slashee.main;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

public class Slashee {

public static void main(String[] args) {
    	
    	/* List<Map<String, String>> profilePerUser = SlackHandler.getProfilePerUser();
    	for (Map<String, String> profile : profilePerUser) {
    		for (Map.Entry<String, String> field : profile.entrySet()) {
    		    Printer.info("Key = " + field.getKey() + ", Value = " + field.getValue());
    		}
    		Printer.info("=====================");
    	}
    	*/
		
		try {
			SheetsHandler.main(args);
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			Printer.error(e.getStackTrace().toString());
		}
	
    	/*
    	Map<String, String> idToLabelMap = getIdToLabelMap();
    		
    		if (null != idToLabelMap) {
	    		for (Map.Entry<String, String> field : idToLabelMap.entrySet()) {
	    		    Printer.info("Key = " + field.getKey() + ", Value = " + field.getValue());
	    		}
    		}
    		Printer.info("=====================");  */
    	}

}
