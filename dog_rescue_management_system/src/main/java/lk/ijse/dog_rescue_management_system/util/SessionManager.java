package lk.ijse.dog_rescue_management_system.util;

import lk.ijse.dog_rescue_management_system.dto.UserDto;

public class SessionManager {
    private static SessionManager instance;
    private UserDto currentUser;

    private SessionManager() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(UserDto user) {
        this.currentUser = user;
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}