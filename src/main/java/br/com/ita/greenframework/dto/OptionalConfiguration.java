package br.com.ita.greenframework.dto;

public class OptionalConfiguration extends Configuration {

    private boolean ignore;

    public OptionalConfiguration(boolean ignore, String configurationKey) {
        super(configurationKey);
        this.ignore = ignore;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
