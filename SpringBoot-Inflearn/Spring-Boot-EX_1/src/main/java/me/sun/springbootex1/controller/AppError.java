package me.sun.springbootex1.controller;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/26
 */
public class AppError {
    private String message;
    private String reason;

    public AppError(String message, String reason) {
        this.message = message;
        this.reason = reason;
    }
}
