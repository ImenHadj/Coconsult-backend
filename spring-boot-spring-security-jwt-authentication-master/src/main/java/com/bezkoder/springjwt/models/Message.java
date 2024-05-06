package com.bezkoder.springjwt.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderEmail;
    private Date time = new Date(System.currentTimeMillis());
    private String replymessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message() {
    }

    public Message(String senderEmail, Date time, String replymessage) {
        this.senderEmail = senderEmail;
        this.time = time;
        this.replymessage = replymessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReplymessage() {
        return replymessage;
    }

    public void setReplymessage(String replymessage) {
        this.replymessage = replymessage;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
