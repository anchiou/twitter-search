package com.search.twitter.searcher;

public class RestResponse {
    private int status; // HTTP response code
    private String message;
    private String data;

    public RestResponse(int status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getter and setter methods

    public int getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String value) {
        this.data = value;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
