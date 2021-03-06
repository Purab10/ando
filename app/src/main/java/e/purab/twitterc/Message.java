package e.purab.twitterc;

public class Message {
    private String message;
    private String author;
    private String receiver;

    public Message() {
    }

    public Message(String message, String author, String receiver) {
        this.message = message;
        this.author = author;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReciever() {
        return receiver;
    }

    public void setReciever(String receiver) {
        this.receiver = receiver;
    }
}
