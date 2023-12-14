package com.example.phoneBook.dto;

public class ContactDto {

    private String email;

    private String name;

    private String phoneNumber;

    private boolean isValid;

    private String wrongLine;

    public String getWrongLine() {
        return wrongLine;
    }

    public void setWrongLine(String wrongLine) {
        this.wrongLine = wrongLine;
    }

    public ContactDto() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
