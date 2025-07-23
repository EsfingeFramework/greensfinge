package net.sf.esfinge.greenframework.core.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenConstant {

    //Environments
    public static final double CO2_PER_KWH_IN_KG = 0.4;
    public static final String PERSISTENCE_TYPE_MEMORY = "memory";
    public static final String DEFAULT_PACKAGE = "br.com.ita.greenframework";
    public static final String DEFAULT_UNIT_JOULE = "joule";

    public static final double JOULES_PER_KWH = 3_600_000.0;
    public static final String STR_DEFAULT_VALUE = "greenDefaultValue";
    public static final double DOUBLE_DEFAULT_VALUE = 999999999.9;
    public static final String FIELD_ORIGINAL_BEAN = "originalBean";
}
