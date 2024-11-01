package org.example.spring_sql.model;

public class Message {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Message() {}

    public Message(String message) {
        this.message = message;
    }

    public String toString() {
        return "Message{" +
                "message='" + message +
                '}';
    }
}
