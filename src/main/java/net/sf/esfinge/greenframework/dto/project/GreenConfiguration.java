package net.sf.esfinge.greenframework.dto.project;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreenConfiguration {

    private String className;
    private String fieldName;
    private String annotation;
    private Map<String, Object> configurationValues;

}
