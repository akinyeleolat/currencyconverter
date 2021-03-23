package com.currencyconverter.web.dto;

public class MessageDTO {
    private String message;

    private String description;

    public MessageDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public MessageDTO() {
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}