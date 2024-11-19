package br.com.ita.greenframework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GreenNumberConfiguration extends GreenDefaultConfiguration {

    private Number value;

    public GreenNumberConfiguration(String configurationKey, Number value) {
        super(configurationKey);
        this.value = value;
    }

}
