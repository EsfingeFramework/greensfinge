package net.sf.esfinge.greenframework.spring.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "green.framework")
public class GreensfingeConfiguration {

    private boolean enable;

    public boolean isEnabled() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
