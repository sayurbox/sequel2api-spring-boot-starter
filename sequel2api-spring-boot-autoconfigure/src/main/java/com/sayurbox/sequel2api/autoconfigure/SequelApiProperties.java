package com.sayurbox.sequel2api.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

// ref https://stackoverflow.com/questions/56152340/how-to-conditionally-make-a-spring-boot-application-terminate-at-startup-based-o
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
