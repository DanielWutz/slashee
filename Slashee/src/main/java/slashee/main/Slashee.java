package slashee.main;

import java.util.List;
import java.util.Map;

public class Slashee {

public static void main(String[] args) {
    	
    	List<Map<String, String>> profilePerUser = SlackHandler.getProfilePerUser();
    	for (Map<String, String> profile : profilePerUser) {
    		for (Map.Entry<String, String> field : profile.entrySet()) {
    		    Printer.info("Key = " + field.getKey() + ", Value = " + field.getValue());
    		}
    		Printer.info("=====================");
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
