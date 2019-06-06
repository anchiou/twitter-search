package com.search.twitter.searcher;

public class RestResponse {
    private int status; // HTTP response code
    private String message;
    private Object data;

    public RestResponse(int status, String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object value) {
        this.data = value;
    }

    @Override
    public String toString() {
        return JSONUtils.toJson(this);
    }
}
