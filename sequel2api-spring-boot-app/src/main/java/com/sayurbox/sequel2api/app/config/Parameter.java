package com.sayurbox.sequel2api.app.config;

public abstract class Parameter {

    protected String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Object newParamValue(String value) {
        switch (type) {
            case "Integer":
                return Integer.parseInt(value);
            case "String":
                return value;
            case "Long":
                return Long.parseLong(value);
            case "Double":
                return Double.parseDouble(value);
            default:
                throw new IllegalArgumentException("Not supported parameter :"+ type);
        }
    }

}
