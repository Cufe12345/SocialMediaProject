package socialmedia;

/**
 * This class represents a comment in the social media system.
 * It contains the handle of the user who posted the message,
 * the message itself, and the id of the post.
 * it inherits from the Post class
 */

public class Comment extends Post {
    private int parentId;

    /**
     * This constructor creates a new comment with the given handle, message, and id
     * and stores the parent post ID.
     */
    public Comment(String handle, String message, int parentId, int nextPostId) {
        super(handle, message, nextPostId);
        this.parentId = parentId;
    }

    /**
     * This method returns the id of the parent post.
     */
    public int getParentId() {
        // TODO Auto-generated method stub
        return this.parentId;
    }

    /**
     * This method sets the id of the parent post.
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
