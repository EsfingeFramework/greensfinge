package net.sf.esfinge.greenframework.core.dto.annotation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@JsonInclude(NON_NULL)
public class GreenCustomMockConfiguration {

    private String key;
    private String returnType;
    private String customClass;
    private String defaultValue;

    @SneakyThrows
    public Map<String, Object> toMap() {
        return new ObjectMapper().readValue(this.getDefaultValue(), new TypeReference<>() {});
    }
}
