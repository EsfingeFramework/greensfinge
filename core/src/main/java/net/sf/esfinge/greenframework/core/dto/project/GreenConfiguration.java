package net.sf.esfinge.greenframework.core.dto.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@ToString
public class GreenConfiguration {

    private String key;
    private String configurations;

    @SneakyThrows
    public Map getConfigurationsMap() {
        return new ObjectMapper().readValue(configurations, HashMap.class);
    }
}
