package br.com.ita.greenframework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GreenOptionalConfiguration extends GreenDefaultConfiguration {

    private boolean ignore;

    public GreenOptionalConfiguration(boolean ignore, String configurationKey) {
        super(configurationKey);
        this.ignore = ignore;
    }

}
