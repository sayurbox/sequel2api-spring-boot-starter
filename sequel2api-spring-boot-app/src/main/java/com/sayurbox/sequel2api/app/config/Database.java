package com.sayurbox.sequel2api.app.config;

public class Database {

    private DbType type;
    private String hostname;
    private Integer port;
    private String username;
    private String password;
    private String name;

    private String overrideDataSource;

    public DbType getType() {
        return type;
    }

    public void setType(DbType type) {
        this.type = type;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverrideDataSource() {
        return overrideDataSource;
    }

    public void setOverrideDataSource(String overrideDataSource) {
        this.overrideDataSource = overrideDataSource;
    }
}
