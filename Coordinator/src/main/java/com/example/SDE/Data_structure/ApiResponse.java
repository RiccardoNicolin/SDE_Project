package com.example.SDE.Data_structure;

public class ApiResponse {

    private String message;
    private boolean status;

    public ApiResponse(String string, boolean b) {
        message = string;
        status = b;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
