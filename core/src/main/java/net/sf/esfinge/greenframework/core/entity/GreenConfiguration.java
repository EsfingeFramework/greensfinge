package net.sf.esfinge.greenframework.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GreenConfiguration {

    private String key;
    private String keyContext;
    private String configurations;

}
