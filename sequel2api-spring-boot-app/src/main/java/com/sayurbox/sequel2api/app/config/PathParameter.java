package com.sayurbox.sequel2api.app.config;

public class PathParameter extends Parameter {

    private String name;
    private String binding;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

}
