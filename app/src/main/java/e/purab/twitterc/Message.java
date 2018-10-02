package e.purab.twitterc;

public class Message {
    private String message;
    private String author;
    private String photoUrl;

    public Message() {
    }

    public Message(String message, String author, String photoUrl) {
        this.message = message;
        this.author = author;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
