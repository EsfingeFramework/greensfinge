package br.com.ita.greenframework;

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
