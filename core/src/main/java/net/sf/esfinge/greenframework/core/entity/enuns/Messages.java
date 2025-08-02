package net.sf.esfinge.greenframework.core.entity.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Messages {

    ERROR_CUSTOM_MOCK_DEFAULT_VALUE("The default value of custom mock {0} value is not a valid json!"),
    ERROR_PERSISTENCE_TYPE("Persistence type {0} not implemented!"),
    ERROR_CUSTOM_MOCK_NOT_INSTANCE("Your custom mock implementation {0} does not extend the 'GreenCustomMockProvider'!"),
    ERROR_CUSTOM_MOCK_RETURN_TYPE ("The provided configuration is of type {0} and the return of method processCustomMockReturn is {1}. Please adjust your implementation to return {2} !")
    ;

    private final String message;

    public String getMessage(Object... args) {
        String result = message;
        if (Objects.nonNull(args)) {
            for (int i = 0; i < args.length; i++) {
                if(Objects.nonNull(args[i])) {
                    result = result.replace("{" + i + "}", args[i].toString());
                }
            }
        }
        return result;
    }
}
