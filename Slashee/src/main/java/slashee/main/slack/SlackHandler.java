package slashee.main.slack;

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
import slashee.main.ConfigHandler;
import slashee.main.Printer;
import slashee.main.slack.model.SlackProfile;

public class SlackHandler {
	
	private static final String slackToken = ConfigHandler.getSlackToken();
	private static final Slack slack = Slack.getInstance();
	
	public static List<String> getUserIds() {
    	try {
			UsersListResponse usersList = slack.methods().usersList(UsersListRequest.builder()
					.token(slackToken)
					.build());
			List<String> ids = new ArrayList<>();
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
	
	@SuppressWarnings("deprecation")
	public static List<SlackProfile> getProfilePerUser() {
		final List<String> userIds = getUserIds();
    	final Map<String, String> idToLabelMap = getIdToLabelMap();
    	final List<SlackProfile> profilePerUser = new ArrayList<>();
    	for (final String id : userIds) {
    		final Profile profile = getProfile(id);
    		final String firstName = profile.getFirstName();
    		if (ConfigHandler.getIncludeGuestUsers() || (null != firstName && !firstName.isEmpty())) {
	    		final String lastName = profile.getLastName();
	    		final Map<String, String> profileMap = new HashMap<>();
	    		
	    		// default fields
	    		
	    		
	    		if (null != id) 		profileMap.put("Id", id);
	    		if (null != firstName)	profileMap.put("First name", profile.getFirstName());
	    		if (null != lastName) 	profileMap.put("Last name", lastName);
	    		if (null != id) 		profileMap.put("Slack URL", ConfigHandler.getSlackUrl() + "/team/" + id);
	    		
	    		// custom fields
	    		final Map<String, Field> fields = profile.getFields();
	    		
	    		if (null != fields) {
		    		for (Map.Entry<String, Field> field : fields.entrySet()) {
		    		    profileMap.put(idToLabelMap.get(field.getKey()), field.getValue().getValue());
		    		}
	    		}
	    		
	    		// sometimes, Email is not filled, but alternate Email is :-O
	    		final String emailKey = ConfigHandler.getEmailKey();
	    		final String alternateEmailKey = ConfigHandler.getAlternateEmailKey();
	    		final String email = profileMap.get(emailKey);
	    		final String alternateEmail = profileMap.get(alternateEmailKey);
	    		
	    		if (null == email && null != alternateEmail) {
	    			profileMap.put(emailKey, alternateEmail);	// move alternate Email to Email
	    			profileMap.remove(alternateEmailKey);		// remove alternate Email
	    		}
	    		
	    		profilePerUser.add(new SlackProfile(profileMap));
    		}
    	}
    	return profilePerUser;
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
