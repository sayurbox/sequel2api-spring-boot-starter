package com.sayurbox.sequel2api.app.client;

import org.springframework.http.ResponseEntity;

public class Response {

    private boolean success;
    private String error;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseEntity<Response> badRequestResponse(String error) {
        Response response = new Response();
        response.setError(error);
        response.setSuccess(false);
        return ResponseEntity.badRequest().body(response);
    }

    public static ResponseEntity<Response> okResponse(Object data) {
        Response response = new Response();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

}
