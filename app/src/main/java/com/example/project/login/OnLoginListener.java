package com.example.project.login;

/**
 * Login event listener
 */
public interface OnLoginListener {

    /**
     * Called when a login event occurs.
     * @param uuid The UUID of the logged in user.
     */
    void onLogin(String uuid);

    /**
     * Called when a logout event occurs.
     */
    void onLogout();

    /**
     * Called when a register event occurs.
     * @param uuid The UUID of the registered in user.
     */
    void onRegister(String uuid);

    /**
     * Called when an error occurs in login/register.
     * @param message
     */
    void onError(String message);
}
