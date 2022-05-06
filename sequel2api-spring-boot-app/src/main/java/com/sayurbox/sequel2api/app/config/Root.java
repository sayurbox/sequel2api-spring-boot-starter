package com.sayurbox.sequel2api.app.config;

import java.util.ArrayList;
import java.util.List;

public class Root {

    private Database database;
    private List<Api> api;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public List<Api> getApi() {
        if (api == null) {
            return new ArrayList<>();
        }
        return api;
    }

    public void setApi(List<Api> api) {
        this.api = api;
    }

}
