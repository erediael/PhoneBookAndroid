package com.fmi.gvarbanov.phonebook.models;

public class PhoneContact {
    private int Id;
    private String contactName;
    private String phoneNumber;
    private String additionalInfo;
    private String category;

    public PhoneContact() {

    }

    public PhoneContact(int id, String contactName, String phoneNumber, String additionalInfo, String category) {
        Id = id;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.additionalInfo = additionalInfo;
        this.category = category;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return this.contactName;
    }
}
