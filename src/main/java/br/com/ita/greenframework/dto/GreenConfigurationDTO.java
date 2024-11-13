package br.com.ita.greenframework.dto;

public class GreenConfigurationDTO {

    private String className;
    private String fieldName;
    private String annotation;
    private String configurationKey;

    public GreenConfigurationDTO(String className, String fieldName, String annotation, String configurationKey) {
        this.className = className;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.configurationKey = configurationKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "GreenConfigurationDTO{" +
                "className='" + className + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", annotation='" + annotation + '\'' +
                ", configurationKey='" + configurationKey + '\'' +
                '}';
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public void setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
    }
}
