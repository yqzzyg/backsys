package com.neusoft.mid.ec.api.exception;

public class JacksonDecoderException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1899319486368694211L;

    /**
     * 
     */
    public JacksonDecoderException() {
    super();
    }

    /**
     * @param message
     * @param cause
     */
    public JacksonDecoderException(String message, Throwable cause) {
    super(message, cause);
    }

    /**
     * @param message
     */
    public JacksonDecoderException(String message) {
    super(message);
    }

    /**
     * @param cause
     */
    public JacksonDecoderException(Throwable cause) {
    super(cause);
    }

}
