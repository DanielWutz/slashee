package slashee.main.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import slashee.main.slack.model.SlackProfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class GSheets {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Knowledge Champion Slack export";

    private static final String SHEETS_ID = "1-ahD0IPnBjY_fIo7Rve18g0Rt1ht0tLkVAvwvws1-mU";
    private static final String RANGE = "Data from Slack!B2";

    private final FileDataStoreFactory dataStoreFactory;
    private final JsonFactory jsonFactory;
    private final HttpTransport httpTransport;
    private final List<String> scopes;

    public GSheets(final FileDataStoreFactory dataStoreFactory, final JsonFactory jsonFactory,
                   final HttpTransport httpTransport, final List<String> scopes) {
        this.dataStoreFactory = checkNotNull(dataStoreFactory);
        this.jsonFactory = checkNotNull(jsonFactory);
        this.httpTransport = checkNotNull(httpTransport);
        this.scopes = checkNotNull(scopes);
    }

    public void write(final List<SlackProfile> slackProfiles) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();

        List<List<Object>> values = new LinkedList<>();

        slackProfiles.forEach(slackProfile -> {
            List<Object> t = Arrays.asList(
                slackProfile.firstName(), slackProfile.lastName(), slackProfile.email(), slackProfile.slackUrl(),
                    slackProfile.displayName(), slackProfile.whatIDo(), slackProfile.phoneNumber(), slackProfile.title(),
                    slackProfile.foundingExperience(), slackProfile.myTeam(), slackProfile.myTeamMates(), slackProfile.mobilePhoneNumber(),
                    slackProfile.methodExperience(), slackProfile.utumRoomNumber(), slackProfile.techExperience(),
                    slackProfile.trainerExperience(), slackProfile.utumIndustryFocus(), slackProfile.background(),
                    slackProfile.priorEmployer(), slackProfile.linkedIn(), slackProfile.interests(), slackProfile.twoTruthsOneLie(),
                    slackProfile.birthdate()
            );

            values.add(t);
        });

        ValueRange body = new ValueRange().setValues(values);
        service.spreadsheets().values().update(SHEETS_ID, RANGE, body)
                .setValueInputOption("RAW")
                .execute();
    }

    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = getClass().getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, jsonFactory, clientSecrets, scopes)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();

        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
    }

    private Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}