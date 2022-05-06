package com.sayurbox.sequel2api.app.config;

import java.util.ArrayList;
import java.util.List;

public class Api {

    private String name;
    private String url;
    private String query;
    private boolean singleResult;
    private List<QueryParameter> queryParameters;
    private List<PathParameter> pathParameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isSingleResult() {
        return singleResult;
    }

    public void setSingleResult(boolean singleResult) {
        this.singleResult = singleResult;
    }

    public List<QueryParameter> getQueryParameters() {
        if (queryParameters == null) {
            return new ArrayList<>();
        }
        return queryParameters;
    }

    public void setQueryParameters(List<QueryParameter> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public List<PathParameter> getPathParameters() {
        if (pathParameters == null) {
            return new ArrayList<>();
        }
        return pathParameters;
    }

    public void setPathParameters(List<PathParameter> pathParameters) {
        this.pathParameters = pathParameters;
    }
}
