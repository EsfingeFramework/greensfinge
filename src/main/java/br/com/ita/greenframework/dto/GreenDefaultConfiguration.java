package br.com.ita.greenframework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class GreenDefaultConfiguration {

    private String configurationKey;

}
