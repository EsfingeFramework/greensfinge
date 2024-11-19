package br.com.ita.greenframework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GreenConfiguration {

    private String className;
    private String fieldName;
    private String annotation;
    private Map<String, Object> configurationValues;

}
