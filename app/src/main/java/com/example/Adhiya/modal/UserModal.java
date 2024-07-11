package com.example.Adhiya.modal;

public class UserModal {
    private String UserId;
    private String Password;
    private String ApiKey;

    public UserModal(String userId, String password, String apiKey) {
        UserId = userId;
        Password = password;
        ApiKey = apiKey;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }
}