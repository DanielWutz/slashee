package slashee.main.slack.model;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class SlackProfile {

    private static class SlackProfileKeys {
        static final String FIRST_NAME = "First name";
        static final String LAST_NAME = "Last name";
        static final String SLACK_URL = "Slack URL";
        static final String EMAIL = "Email";
        static final String DISPLAY_NAME = "Display name"; // TODO
        static final String WHAT_I_DO = "How I can help you";
        static final String PHONE_NUMBER = "Phone number";
        static final String TITLE = "Title";
        static final String MY_TEAM = "My Team";
        static final String MY_TEAM_MATES = "My Team mates";
        static final String MOBILE_PHONE_NUMBER = "Mobile Phone Number";
        static final String UTUM_ROOM_NUMBER = "UTUM Room Number";
        static final String FOUNDING_EXPERIENCE = "Founding Experience";
        static final String METHOD_EXPERIENCE = "Method Experience";
        static final String TECH_EXPERIENCE = "Tech Experience";
        static final String TRAINER_EXPERIENCE = "Trainer Experience";
        static final String UTUM_INDUSTRY_FOCUS = "UTUM Industry Focus";
        static final String BACKGROUND = "Background";
        static final String PRIOR_EMPLOYER = "Prior Employer";
        static final String LINKED_IN = "LinkedIn";
        static final String INTERSTS = "Interests & Hobbies";
        static final String TWO_TRUTH_ONE_LIE = "2 Truths, 1 Lie";
        static final String BIRTHDATE = "Birthdate";


    }

    private final Map<String, String> profileData;

    public SlackProfile(Map<String, String> profileData) {
        this.profileData = checkNotNull(profileData);
    }

    public String firstName() {
        return getOrEmpty(SlackProfileKeys.FIRST_NAME);
    }

    public String lastName() {
        return getOrEmpty(SlackProfileKeys.LAST_NAME);
    }

    public String email() {
        return getOrEmpty(SlackProfileKeys.EMAIL);
    }

    public String slackUrl() {
        return getOrEmpty(SlackProfileKeys.SLACK_URL);
    }

    public String displayName() {
        return getOrEmpty(SlackProfileKeys.DISPLAY_NAME);
    }

    public String whatIDo() {
        return getOrEmpty(SlackProfileKeys.WHAT_I_DO);
    }

    public String phoneNumber() {
        return getOrEmpty(SlackProfileKeys.PHONE_NUMBER);
    }

    public String title() {
        return getOrEmpty(SlackProfileKeys.TITLE);
    }

    public String foundingExperience() {
        return getOrEmpty(SlackProfileKeys.FOUNDING_EXPERIENCE);
    }
    public String myTeam() {
        return getOrEmpty(SlackProfileKeys.MY_TEAM);
    }
    public String myTeamMates() {
        return getOrEmpty(SlackProfileKeys.MY_TEAM_MATES);
    }
    public String mobilePhoneNumber() {
        return getOrEmpty(SlackProfileKeys.MOBILE_PHONE_NUMBER);
    }
    public String utumRoomNumber() {
        return getOrEmpty(SlackProfileKeys.UTUM_ROOM_NUMBER);
    }
    public String methodExperience() {
        return getOrEmpty(SlackProfileKeys.METHOD_EXPERIENCE);
    }
    public String techExperience() {
        return getOrEmpty(SlackProfileKeys.TECH_EXPERIENCE);
    }
    public String trainerExperience() {
        return getOrEmpty(SlackProfileKeys.TRAINER_EXPERIENCE);
    }
    public String utumIndustryFocus() {
        return getOrEmpty(SlackProfileKeys.UTUM_INDUSTRY_FOCUS);
    }
    public String background() {
        return getOrEmpty(SlackProfileKeys.BACKGROUND);
    }
    public String priorEmployer() {
        return getOrEmpty(SlackProfileKeys.PRIOR_EMPLOYER);
    }
    public String linkedIn() {
        return getOrEmpty(SlackProfileKeys.LINKED_IN);
    }
    public String interests() {
        return getOrEmpty(SlackProfileKeys.INTERSTS);
    }
    public String twoTruthsOneLie() {
        return getOrEmpty(SlackProfileKeys.TWO_TRUTH_ONE_LIE);
    }
    public String birthdate() {
        return getOrEmpty(SlackProfileKeys.BIRTHDATE);
    }

    private String getOrEmpty(final String key) {
        return profileData.getOrDefault(key, "");
    }


}

