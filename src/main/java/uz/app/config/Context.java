package uz.app.config;

import uz.app.entity.User;

public class Context {
//    static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
//    private static User user;
//    public static User getUser() {
//        return userThreadLocal.get();
//    }
//    public static void setUser(User user) {
//        userThreadLocal.set(user);
//    }

    public static User userThreadLocal;
//    private static User user;
    public static User getUser() {
        return userThreadLocal;
    }
    public static void setUser(User user) {
        userThreadLocal = user;
    }
}
