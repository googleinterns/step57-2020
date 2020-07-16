package data;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Class to build Login/Logout response objects in JSON format */
public class ResponseBuilder {

  /**
   * @param isLoggedIn user login status
   * @param redirectURL URL to redirect after login/logout
   * @return JSON string with login/logout URL
   *         e.g., logged in: { "isLoggedIn":{"logoutURL":"/_ah/logout?continue=%2FLogin"}, "isLoggedOut":{}}
   *         logged out: { "isLoggedIn":{}, "isLoggedOut":{"loginURL":"/_ah/login?continue=%2FLogin"}}
   */
  public String toJson(boolean isLoggedIn, String redirectURL) {
    return "{ \"isLoggedIn\":{" + buildLogout(isLoggedIn, redirectURL) + "}, " +
            "\"isLoggedOut\":{" + buildLogin(isLoggedIn, redirectURL) + "}}";
  }

  private String buildLogin(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? "" : "\"loginURL\":\"" + getLoginURL(redirectURL) + '"';
  }

  private String buildLogout(boolean isLoggedIn, String redirectURL) {
    return isLoggedIn ? "\"logoutURL\":\"" + getLogoutURL(redirectURL) + '"' : "";
  }

  /**
   * @param redirect URL for webpage to return to after login.
   * @return URL for user to click to login.
   */
  private String getLoginURL(String redirect) {
    return UserServiceFactory.getUserService().createLoginURL(redirect);
  }

  /**
   * @param redirect URL for webpage to return to after logout.
   * @return URL for user to click to logout.
   */
  private String getLogoutURL(String redirect) {
    return UserServiceFactory.getUserService().createLogoutURL(redirect);
  }

}
