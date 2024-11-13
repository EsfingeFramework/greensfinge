package br.com.ita.greenframework.dto;

public abstract class Configuration {

    private String configurationKey;

    public Configuration(String configurationKey) {
        this.configurationKey = configurationKey;
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public void setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
    }
}
