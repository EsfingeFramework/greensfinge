package br.com.ita.greenframework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class GreenNumberConfiguration extends GreenDefaultConfiguration {

    private Number value;

}
