package com.assignment;

public class Message {
    public String username;
    public String message;

    public Message() {
        this.username = "Could not read username";
        this.message = "Could not read message";
    }

    public Message(String usr, String msg) {
        this.username = usr;
        this.message = msg;
    }

    public Message hashMe() {
        String hashCode = String.valueOf(this.username.hashCode());
        return new Message(hashCode, message);
    }
}
