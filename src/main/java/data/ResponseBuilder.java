package data;

import util.UserAuthUtil;

/** Class to build Login/Logout response objects in JSON format */
public class ResponseBuilder {
  /**
   * @param isLoggedIn user login status
   * @param redirectURL URL to redirect after login/logout
   * @return JSON string with login/logout URL
   *         e.g., logged in: { "isLoggedIn":{"/_ah/logout?continue=%2FLogin"}, "isLoggedOut":{""}}
   *         logged out: { "isLoggedIn":{""}, "isLoggedOut":{"/_ah/login?continue=%2FLogin"}}
   */
  public static String toJson(boolean isLoggedIn, String redirectURL) {
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
