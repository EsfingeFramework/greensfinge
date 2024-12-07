package br.com.ita.greenframework.dto.annotation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class GreenAdjustableNumberConfiguration extends GreenDefaultConfiguration {

    private Number value;

}
