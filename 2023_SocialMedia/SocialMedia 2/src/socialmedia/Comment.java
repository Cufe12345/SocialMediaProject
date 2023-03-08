package socialmedia;

public class Comment extends Post {
    public int ogID;

    public Comment(String handle, String message, int ogID) {
        super(handle, message);
        this.ogID = ogID;
    }
}
