package com.example.project.login;

/**
 * Login event listener
 */
public interface OnLoginListener {

    /**
     * Called when a login event occurs.
     * @param UUID the UUID of the logged in user.
     */
    void onLogin(String UUID);

    /**
     * Called when a logout event occurs.
     */
    void onLogout();

    /**
     * Called when a register event occurs.
     * @param UUID the UUID of the registered user.
     */
    void onRegister(String UUID);

    /**
     * Called when an error occurs in login/register.
     * @param message the error message.
     */
    void onError(String message);
}
