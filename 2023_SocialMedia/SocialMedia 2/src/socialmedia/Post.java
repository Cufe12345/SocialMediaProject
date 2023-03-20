package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private int id;
    private String handle;
    private String message;
    public static Post emptyPost = new Post(null,
            "The original content was removed from the system and is no longer available.",0);
    

    public Post(String handle, String message,int nextPostId) {
        this.id = nextPostId;
        nextPostId++;
        this.handle = handle;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getMessage() {
        return message;
    }

   
    

}
