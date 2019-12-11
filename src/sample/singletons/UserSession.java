package sample.singletons;
import sample.models.UserInfo;
import java.util.prefs.Preferences;



/////<---*||*--->/////
public class UserSession {
    private static UserSession ourInstance = new UserSession();
    private static Preferences preferences = Preferences.userRoot().node(UserSession.class.getName());

    public static UserSession getInstance() {
        return ourInstance;
    }

    private UserSession() {
    }


    public void saveUserInfo(UserInfo userInfo) {
        preferences.put("email", userInfo.getEmail());
        preferences.put("password", userInfo.getPassword());
    }


    public UserInfo getUserInfo() {

        return new UserInfo(preferences.get("email", "session"), preferences.get("password", "session"));
    }


    public boolean weHaveAuser() {

        System.out.println(preferences.get("email", null));
        System.out.println(preferences.get("password", null));


        try {
            return preferences.get("email", null) != null && preferences.get("password", null) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void destroyUserInfo() {
        preferences.remove("email");
        preferences.remove("password");
    }


}
