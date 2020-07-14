package data;

import util.UserAuthUtil;

public class ResponseBuilder {
  public static String toJson(boolean isLoggedIn, String redirectURL) {
    // sample output (logged in): { "isLoggedIn" : {"logoutURL"}, "isLoggedOut" : {}}
    // sample output (logged out): { "isLoggedIn" : {}, "isLoggedOut" : { "loginURL" }}
    return "{ \"isLoggedIn\":{" + buildLogout(isLoggedIn, redirectURL) + "}, \"isLoggedOut\":{"
            + buildLogin(isLoggedIn, redirectURL) + "}}";
  }

  private static String buildLogin(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? "" : UserAuthUtil.getLoginURL(redirectURL);
  }

  private static String buildLogout(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? UserAuthUtil.getLogoutURL(redirectURL) : "";
  }
}
