package com.top.queenbee;

public class Information {
    private String id;
    private String subject;

    // private int IconImage;
    public Information(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }



    public String getId() {
        return id;
    }
    public String getSubject() {
        return subject;
    }
}
