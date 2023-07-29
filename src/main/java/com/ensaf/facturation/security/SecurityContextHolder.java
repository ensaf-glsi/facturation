package com.ensaf.facturation.security;

import com.ensaf.facturation.security.model.UserAuthentication;

/**
 * SecurityContextHolder is a class that provides access to the security context.
 * This class uses a ThreadLocal to make the security context specific to the current thread of execution.
 */
public class SecurityContextHolder {
    /**
     * The ThreadLocal that holds the security context.
     */
    private static final ThreadLocal<UserAuthentication> context = new ThreadLocal<>();

    /**
     * Sets the security context for the current thread of execution.
     * @param authentication the UserAuthentication object to be set in the security context.
     */
    public static void setContext(UserAuthentication authentication) {
        context.set(authentication);
    }

    /**
     * Gets the security context for the current thread of execution.
     * @return the UserAuthentication object from the security context.
     */
    public static UserAuthentication getContext() {
        return context.get();
    }

    /**
     * Clears the security context for the current thread of execution.
     */
    public static void clearContext() {
        context.remove();
    }
}
