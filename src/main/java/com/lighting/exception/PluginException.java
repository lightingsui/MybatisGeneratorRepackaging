package com.lighting.exception;

/**
 * @author ：Lightingsui
 * @since ：2020/2/29 22:32
 */
public class PluginException extends RuntimeException {
    private String message;

    public PluginException(String message) {
        super(message);
        this.message = message;
    }
}
