package com.lighting.exception;

import lombok.Getter;

/**
 * @author ：Lightingsui
 * @since ：2020/2/29 22:16
 */
@Getter
public class YamlException extends RuntimeException {
    private String message;
    private static String DEFAULT_MESSAGE = "Yaml has an exception, check your yaml or yml configuration";

    public YamlException() {
        super(DEFAULT_MESSAGE);
        this.message = DEFAULT_MESSAGE;
    }

    public YamlException(String message) {
        this.message = message;
    }
}
