package com.lighting.exception;

import lombok.Getter;

/**
 * jdbc连接参数异常
 *
 * @author ：隋亮亮
 * @since ：2020/2/28 16:12
 */
@Getter
public class JDBCParamException extends RuntimeException {
    private String exceptionMessage;
    private static final String DEFAULT_EXCEPTION_MESSAGE = "jdbc connect params exception";

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public JDBCParamException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
        this.exceptionMessage = DEFAULT_EXCEPTION_MESSAGE;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param exceptionMessage the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public JDBCParamException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }
}
