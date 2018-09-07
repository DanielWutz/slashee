package slashee.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.request.users.profile.UsersProfileGetRequest;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.model.User.Profile;
import com.github.seratch.jslack.api.model.User.Profile.Field;

public class SlackHandler {
	
	private static final String slackToken = ConfigHandler.getSlackToken();
	private static final Slack slack = Slack.getInstance();
	
	public static List<String> getUserIds() {
    	try {
			UsersListResponse usersList = slack.methods().usersList(UsersListRequest.builder()
					.token(slackToken)
					.build());
			List<String> ids = new ArrayList<String>();
			for (User user : usersList.getMembers()) {
					if (ConfigHandler.getIncludeDeletedUsers() || !user.isDeleted()) {
						ids.add(user.getId());
					}
			}
			return ids;
		} catch (IOException e) {
			Printer.errorIO();
		} catch (SlackApiException e) {
			Printer.errorSlackApi();
		}
    	return null;
	}
	
	public static Map<String, String> getIdToLabelMap() {
		try {
			
			TeamProfileGetResponse teamProfile = slack.methods().teamProfileGet(TeamProfileGetRequest.builder()
					.token(slackToken)
					.visibility(ConfigHandler.getIncludeHiddenFields() ? "all" : "visible")
					.build());
			if (null == teamProfile || !teamProfile.isOk() || teamProfile.getProfile() == null || teamProfile.getProfile().getFields() == null) {
				return null;
			}
			
			Map<String, String> idToLabelMap = new HashMap<String, String>();
			for (com.github.seratch.jslack.api.model.Team.Profile profile : teamProfile.getProfile().getFields()) {
				idToLabelMap.put(profile.getId(), profile.getLabel());
			}
			
			return idToLabelMap;
		} catch (IOException e) {
			Printer.errorIO();
		} catch (SlackApiException e) {
			Printer.errorSlackApi();
		}
    	return null;
	}
	
	public static List<Map<String, String>> getProfilePerUser() {
		List<String> userIds = getUserIds();
    	Map<String, String> idToLabelMap = getIdToLabelMap();
    	List<Map<String, String>> profilePerUser = new ArrayList<Map<String, String>>();
    	//TODO
    	Integer count = 0;
    	for (String id : userIds) {
    		Map<String, String> profile = new HashMap<String, String>();
    		Map<String, Field> fields = getProfile(id).getFields();
    		if (null != fields) {
	    		for (Map.Entry<String, Field> field : fields.entrySet()) {
	    		    profile.put(idToLabelMap.get(field.getKey()), field.getValue().getValue());
	    		}
    		}
    		profilePerUser.add(profile);
    		// TODO remove
    		Printer.info((++count).toString());
    	}
    	return profilePerUser;
	}
	
    public static void main(String[] args) {
    	
    	List<Map<String, String>> profilePerUser = getProfilePerUser();
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
    
    private static Profile getProfile(String id) {
    	try {
			UsersProfileGetResponse userProfile = slack.methods().usersProfileGet(UsersProfileGetRequest.builder()
					.token(slackToken)
					.user(id)
					.build());
			if (null == userProfile || !userProfile.isOk()) {
				return null;
			}
			return userProfile.getProfile();
		} catch (IOException e) {
			Printer.errorIO();
			return null;
		} catch (SlackApiException e) {
			Printer.errorSlackApi();
			return null;
		}
	}
}
