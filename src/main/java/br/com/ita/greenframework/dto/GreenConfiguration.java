package br.com.ita.greenframework.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GreenConfiguration {

    private String className;
    private String fieldName;
    private String annotation;
    private Map<String, Object> configurationValues;

}
