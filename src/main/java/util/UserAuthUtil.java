package util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserAuthUtil {
  public static boolean isUserLoggedIn() {
    UserService userServ = UserServiceFactory.getUserService();
    return userServ.isUserLoggedIn();
  }
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