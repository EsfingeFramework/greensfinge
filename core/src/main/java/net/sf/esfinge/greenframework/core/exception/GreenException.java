package net.sf.esfinge.greenframework.core.exception;

public class GreenException extends RuntimeException {

    private final String message;

    public GreenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
