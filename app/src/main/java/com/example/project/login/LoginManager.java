package com.example.project.login;

/***
 * Defines the structure for the login manager.
 */
public interface LoginManager {

    /**
     * Set the on click listener for the manager
     * @param onLoginListener the listener.
     */
    void setOnLoginListener(OnLoginListener onLoginListener);

    /**
     * Attempt a login to the application
     * @param username The login username
     * @param password The login password
     */
    void login(String username, String password);

    /**
     * Logout of the application.
     */
    void logout();

    /**
     * Register a new user for the application.
     * @param username The new username.
     * @param password The password.
     * @param passwordCheck Verify the password.
     */
    void register(String username, String password, String passwordCheck);

    /**
     * Determine if there is a user logged in.
     * @return boolean representing if the user is logged in or not.
     */
    boolean isLoggedIn();

    /**
     * Get the username name of the user currently logged in.
     * @return The username.
     */
    String getUsername();

    /**
     * Get the UUID of the user currently logged in.
     * @return The UUID.
     */
    String getUUID();
}
