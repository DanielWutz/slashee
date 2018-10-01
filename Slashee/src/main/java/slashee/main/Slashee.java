package slashee.main;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.common.collect.ImmutableList;
import slashee.main.google.GSheets;
import slashee.main.slack.SlackHandler;
import slashee.main.slack.model.SlackProfile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Slashee {

	private static final java.io.File DATA_STORE_DIR = new java.io.File(".credentials/");

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		final FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
		final List<String> scopes = ImmutableList.of(SheetsScopes.SPREADSHEETS);

		final GSheets sheets = new GSheets(dataStoreFactory, jacksonFactory, httpTransport, scopes);
    	
    	List<SlackProfile> profilePerUser = SlackHandler.getProfilePerUser();
    	sheets.write(profilePerUser);
	}

}
