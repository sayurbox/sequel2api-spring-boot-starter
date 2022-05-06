package com.sayurbox.sequel2api.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "sequel2api.config")
@Validated
public class SequelApiProperties {

    @NotNull
    private String yamlLocation;

    public void setYamlLocation(String yamlLocation) {
        this.yamlLocation = yamlLocation;
    }

    public String getYamlLocation() {
        return yamlLocation;
    }

}
