package socialmedia;

import java.io.IOException;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        Account newAccount = new Account(handle);
        return 0;

    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub

    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        int user = -1;
        // Looks for the account with the passed in handle and sets user to that users
        // id
        for (Account account : Account.allAccounts) {
            if (account.getHandle() == handle) {
                user = account.getId();
            }
        }
        if (user == -1 && handle != null) {
            throw new HandleNotRecognisedException("Invalid Handle Provided");
        }
        if (message.length() == 0 || message.length() > 100) {
            throw new InvalidPostException("Invalid Message Length");
        }
        Post post = new Post(handle, message);
        return post.getId();
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        int index = 0;
        boolean deleted = false;
        int emptyPost = -1;
        for (Post post : Post.allPosts) {
            if (post.getEmptyPost()) {
                emptyPost = post.getId();
            }
        }
        if (emptyPost == -1) {
            try {
                emptyPost = createPost(null,
                        "The original content was removed from the system and is no longer available.");
            } catch (Exception e) {
                throw new PostIDNotRecognisedException("Couldnt Create empty Post");
            }
        }
        for (Post post : Post.allPosts) {
            // Checks if the post is the empty post
            if (!post.getEmptyPost()) {

                // Checks if the post is an endorsement if it is checks if the endorsements
                // parent is the post to delete
                // then deletes it
                if (post instanceof Endorsement) {
                    Endorsement temp = (Endorsement) post;
                    if (temp.ogID == id) {
                        deletePost(temp.getId());
                    }
                }
                // Sets the parent of the comment to the empty post.
                if (post instanceof Comment) {
                    Comment temp = (Comment) post;
                    temp.ogID = emptyPost;
                    // Might need to replace value in Post.allPosts with temo to update

                }
                // Post to delete found and removed
                if (post.getId() == id) {
                    Post.allPosts.remove(index);
                    deleted = true;
                }
                index++;
            }
        }
        if (!deleted) {
            throw new PostIDNotRecognisedException("Post To Be Deleted Not Found");
        }

    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        int endorsements = 0;
        int comments = 0;
        Post thePost = null;
        for (Post post : Post.allPosts) {
            if (post.getId() == id) {
                thePost = post;
            }
            // Checks if post is an endorsement and checks if its parent is id
            if (post instanceof Endorsement) {
                Endorsement temp = (Endorsement) post;
                if (temp.ogID == id) {
                    endorsements++;
                }
            }
            // Checks if post is an comment and checks if its parent is id
            if (post instanceof Comment) {
                Comment temp = (Comment) post;
                if (temp.ogID == id) {
                    comments++;
                }
            }

        }
        if (thePost == null) {
            throw new PostIDNotRecognisedException("Post Not Found");
        }
        String output = "ID: " + Integer.toString(thePost.getId()) + "\nAccount: " + thePost.getHandle()
                + "\nNo. endorsements: " + Integer.toString(endorsements) + " | No. comments: "
                + Integer.toString(comments) + "\n" + thePost.getMessage();
        return output;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNumberOfAccounts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalOriginalPosts() {
        // TODO Auto-generated method stub
        int postCount = 0;
        // Counts all posts which are original post ie not endorsement or comment
        for (Post post : Post.allPosts) {
            if (post instanceof Endorsement) {
                continue;
            }
            if (post instanceof Comment) {
                continue;
            }
            postCount++;
        }
        return postCount;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedPost() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedAccount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void erasePlatform() {
        // TODO Auto-generated method stub

    }

    @Override
    public void savePlatform(String filename) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

    }

}
