package com.lighting.exception;

import lombok.Getter;

/**
 * 没有找到主注解
 *
 * @author ：隋亮亮
 * @since ：2020/2/29 10:16
 */
@Getter
public class NotMainAnnotationException extends RuntimeException {
    private String message;
    private static String DEFAULT_MESSAGE = "The main annotation could not be found";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotMainAnnotationException() {
        super(DEFAULT_MESSAGE);
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotMainAnnotationException(String message) {
        super(message);
        this.message = message;
    }
}
