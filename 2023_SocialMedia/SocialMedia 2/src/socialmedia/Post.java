package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a post in the social media system.
 * It contains the handle of the user who posted the message,
 * the message itself, and the id of the post.
 */

public class Post implements Serializable {
    private int id;
    private String handle;
    private String message;
    public static Post emptyPost = new Post(null,
            "The original content was removed from the system and is no longer available.", 0);

    /**
     * This constructor creates a new post with the given handle, message, and id.
     */
    public Post(String handle, String message, int nextPostId) {

        this.id = nextPostId;
        nextPostId++;
        this.handle = handle;
        this.message = message;
    }

    /**
     * This method returns the id of the post.
     */
    public int getId() {

        return id;
    }

    /**
     * This method returns the handle of the user who posted the message.
     */
    public String getHandle() {

        return handle;
    }

    /**
     * This method returns the message of the post.
     */
    public String getMessage() {

        return message;
    }

    /**
     * This method sets the handle of the user who posted the message.
     */
    public void setHandle(String handle) {

        this.handle = handle;
    }

}
