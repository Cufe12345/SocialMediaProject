package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

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
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == handle) // Lowercase maybe
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")) { // The system limit of characters
                                                                                     // may not be 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each
                                                                        // condition.
        }
        Account newAccount = new Account(handle);
        return newAccount.getId();

    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == handle) // Lowercase maybe
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")) { // The system limit of characters
                                                                                     // may not be 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each
                                                                        // condition.
        }
        Account newAccount = new Account(handle, description);
        return newAccount.getId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        for (Account a : Account.allAccounts) {
            if (a.getId() == id) {
                Account.allAccounts.remove(a); // This may not work?
                return;
            }
        }
        throw new AccountIDNotRecognisedException("This ID was not recognised.");
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == handle) {
                Account.allAccounts.remove(a); // This may not work?
                return; // This might be a bad way of doing it, otherwise have a boolean variable
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        Account tempAccount = null;
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == newHandle) {
                throw new IllegalHandleException("Handle already taken");
            }
            if (a.getHandle() == oldHandle) {
                tempAccount = a;
            }
        }
        if (newHandle.length() == 0 || newHandle.length() > 100 || newHandle.contains(" ")) { // The system limit of
                                                                                              // characters may not be
                                                                                              // 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each
                                                                        // condition.
        }
        if (tempAccount == null) {
            throw new HandleNotRecognisedException("Account handle not recognised");
        } else {
            tempAccount.setHandle(newHandle);
        }

    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == handle) {
                a.setDescription(description); // This may not work?
                return; // This might be a bad way of doing it, otherwise have a boolean variable
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");
    }

    // Callum Add Post Count
    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        String message = "";
        int commentCount = 0;
        int endorsementCount = 0;
        int postCount = 0;
        for (Post post : Post.allPosts) {
            if (post.getHandle() == handle) {
                if (post instanceof Endorsement) {
                    endorsementCount++;
                    continue;
                }
                if (post instanceof Comment) {
                    commentCount++;
                    continue;
                }
                postCount++;
            }
        }
        for (Account a : Account.allAccounts) {
            if (a.getHandle() == handle) {
                message = String.format(
                        "ID: %1$s \n Handle: %2$s \n Description: %3$s \n Post count: %4$s \n Endorse count: %5$s \n Comment count: %6$s",
                        a.getId(),
                        a.getHandle(), a.getDescription(), Integer.toString(postCount),
                        Integer.toString(endorsementCount), Integer.toString(commentCount)); // Do post/endorsement
                                                                                             // count later
                return message;
            }
        }
        throw new HandleNotRecognisedException("This handle was not recognised.");

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
        if (user == -1) {
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
        int user = -1;
        // Looks for the account with the passed in handle and sets user to that users
        // id
        for (Account account : Account.allAccounts) {
            if (account.getHandle() == handle) {
                user = account.getId();
            }
        }
        if (user == -1) {
            throw new HandleNotRecognisedException("Invalid Handle Provided");
        }
        Post thePost = null;
        for (Post post : Post.allPosts) {
            if (post.getId() == id) {
                thePost = post;
            }
        }
        // Checks if the post was found
        if (thePost == null) {
            throw new PostIDNotRecognisedException("Post not found");
        }
        // Checks if the post found was a endorsement
        if (thePost instanceof Endorsement) {
            throw new NotActionablePostException("Cant comment on an endorsement");
        }
        // Checks if the post is the empty post
        if (thePost.getHandle() == null) {
            throw new NotActionablePostException("Cant comment on an empty post");
        }
        // Checks if the message provide is valid
        if (message == null || message.length() > 100) {
            throw new InvalidPostException("Invalid message provided");
        }
        Comment comment = new Comment(handle, message, id);
        return comment.getId();
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        Post emptyPost = null;
        for (Post post : Post.allPosts) {
            if (post.getHandle() == null) {
                emptyPost = post;
            }
        }
        Post toRemove = null;
        for (Post post : Post.allPosts) {
            // Checks if the post is the empty post
            if (post.getHandle() != null) {

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
                    temp.setParentId(emptyPost.getId());
                    // Might need to replace value in Post.allPosts with temo to update

                }
                // Post to delete found and removed
                if (post.getId() == id) {
                    toRemove = post;

                }
            }
        }

        if (toRemove == null) {
            throw new PostIDNotRecognisedException("Post To Be Deleted Not Found");
        }
        Post.allPosts.remove(toRemove);

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
                if (temp.getParentId() == id) {
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
        return Account.allAccounts.size();
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
        int commentCount = 0;
        for (Post post : Post.allPosts) {
            if (post instanceof Comment) {
                commentCount++;
            }
        }
        return commentCount;
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
