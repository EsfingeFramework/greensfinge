package br.com.ita.greenframework.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenConstant {

    public static final String PERSISTENCE_TYPE_MEMORY = "memory";
    public static final String STR_DEFAULT_VALUE = "greenDefaultValue";
    public static final String DEFAULT_PACKAGE = "br.com.ita.greenframework";
    public static final int INT_DEFAULT_VALUE = 999999999;
}
