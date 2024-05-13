package db;
import db.User;

public class Session {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public static void clearSession() {
        user = null;
    }

    public static boolean isUserLoggedIn() {
        return user != null;
    }
}

