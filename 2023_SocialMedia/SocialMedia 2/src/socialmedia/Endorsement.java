package socialmedia;

/**
 * This class represents an endorsement in the social media system.
 * It contains the handle of the user who posted the message,
 * the message itself, and the id of the post.
 * it inherits from the Post class
 */

public class Endorsement extends Post {
    private int ogPostId;

    /**
     * This constructor creates a new endorsement with the given handle, message,
     * and id
     * and stores the parent post ID.
     */
    public Endorsement(String handle, String message, String ogHandle, int ogId, int nextPostId) {
        super(handle, String.format("EP@%1$s: %2$s", ogHandle, message), nextPostId); // Formats message for the
                                                                                      // endorsement post.
        this.ogPostId = ogId;

    }

    /**
     * This method returns the id of the parent post.
     */
    public int getOgId() {
        return ogPostId;
    }
}