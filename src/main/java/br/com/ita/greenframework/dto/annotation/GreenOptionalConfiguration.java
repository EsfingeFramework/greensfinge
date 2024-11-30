package br.com.ita.greenframework.dto.annotation;

import br.com.ita.greenframework.util.GreenConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public class GreenOptionalConfiguration extends GreenDefaultConfiguration {

    private boolean ignore;

    @Builder.Default
    private String strDefaultValue = GreenConstant.STR_DEFAULT_VALUE;
    @Builder.Default
    private Double numberDefaultValue = GreenConstant.DOUBLE_DEFAULT_VALUE;

}
