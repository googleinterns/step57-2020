package data;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/** 
 * A class to create and send an AppIdentityCredential object that allows for Sheets API interaction 
 */
public class SheetsAuthUtil {
  public static AppIdentityCredential authorize() throws IOException, GeneralSecurityException {
         
    // Build GoogleClientSecrets from JSON file.
    InputStream in = SheetsAuthUtil.class.getResourceAsStream("/google-sheets-api.json");
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

    List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
 
    AppIdentityCredential credential = new AppIdentityCredential(scopes);

    return credential;
  }
}

