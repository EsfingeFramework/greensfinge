package net.sf.esfinge.greenframework.dto.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
public abstract class GreenDefaultConfiguration {

    private String configurationKey;

}
