package net.sf.esfinge.greenframework.core.exception;

import net.sf.esfinge.greenframework.core.entity.enuns.Messages;

public class GreenBusinessException extends RuntimeException {

    private final String message;

    public GreenBusinessException(Messages enumMessage, Object... args) {
        this.message = enumMessage.getMessage(args);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
