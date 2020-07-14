package data;

import util.UserAuthUtil;

public class ResponseBuilder {
  public static String toJson(boolean isLoggedIn, String redirectURL) {
    // logged in: { "isLoggedIn":{"/_ah/logout?continue=%2FLogin"}, "isLoggedOut":{""}}
    // logged out: { "isLoggedIn":{""}, "isLoggedOut":{"/_ah/login?continue=%2FLogin"}}
    return "{ \"isLoggedIn\":{\"" + buildLogout(isLoggedIn, redirectURL) + "\"}, " +
            "\"isLoggedOut\":{\"" + buildLogin(isLoggedIn, redirectURL) + "\"}}";
  }

  private static String buildLogin(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? "" : UserAuthUtil.getLoginURL(redirectURL);
  }

  private static String buildLogout(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? UserAuthUtil.getLogoutURL(redirectURL) : "";
  }
}
