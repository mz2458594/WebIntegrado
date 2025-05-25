package com.example.domain.ecommerce.dto;

public class EmailDTO {

    private int id;
   
    private String mailTo;
    private String subject;
    private String userName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMailTo() {
        return mailTo;
    }
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    


}
