package util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthUtil {
  /**
   * Returns a boolean for the user's login status
   * @return user login status
   */
  public static boolean isUserLoggedIn() {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.isUserLoggedIn();
  }

  /**
   * @param redirect URL for webpage to return to after login
   * @return
   */
  public static String getLoginURL(String redirect) {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.createLoginURL(redirect);
  }
  public static String getLogoutURL(String redirect) {
    return UserServiceFactory.getUserService().createLogoutURL(redirect);
  }
  public static User getUser() {
    return UserServiceFactory.getUserService().getCurrentUser();
  }
  public static boolean isUserAuthorized() {
    return getDomainName().equals("google.com");
  }

  private static String getDomainName() {
    String email = getUser().getEmail();
    return email.substring(email.indexOf('@') + 1);
  }
}