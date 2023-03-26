package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
    public ArrayList<Account> allAccounts = new ArrayList<Account>();
    public ArrayList<Post> allPosts = new ArrayList<Post>();

    public int nextPostId = 1;
    public int nextAccountId = 0;

    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
        for (Account a : allAccounts) {
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
        Account newAccount = new Account(handle, nextAccountId);
        nextAccountId++;
        allAccounts.add(newAccount);
        return newAccount.getId();

    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        for (Account a : allAccounts) {
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
        Account newAccount = new Account(handle, description, nextAccountId);
        nextAccountId++;
        allAccounts.add(newAccount);
        return newAccount.getId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        for (Account a : allAccounts) {
            if (a.getId() == id) {
                allAccounts.remove(a); // This may not work?
                return;
            }
        }
        throw new AccountIDNotRecognisedException("This ID was not recognised.");
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        for (Account a : allAccounts) { // Remove all posts associated with that account. IMPORTANT
            if (a.getHandle() == handle) {
                allAccounts.remove(a); // This may not work?
                for (Post p : allPosts) {
                    if (p.getHandle() == handle) {
                        // remove post
                    }
                }
                return; // This might be a bad way of doing it, otherwise have a boolean variable UPDATE
                        // 15.03.2023: This is a bad way of doing it... do it better. Actually might be
                        // ok
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        Account tempAccount = null; // The account handle will need to be updated in posts as well. Has not been
                                    // done yet. Very important to do.
        for (Account a : allAccounts) {
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
        for (Account a : allAccounts) {
            if (a.getHandle() == handle) {
                a.setDescription(description); // This may not work?
                return; // This might be a bad way of doing it, otherwise have a boolean variable
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        String message = "";
        int commentCount = 0;
        int endorsementCount = 0;
        int postCount = 0;
        for (Post post : allPosts) {
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
        for (Account a : allAccounts) {
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
        for (Account account : allAccounts) {
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
        // Creates a post and adds it the allPosts array also increments nextPostId
        Post post = new Post(handle, message, nextPostId);
        nextPostId++;
        allPosts.add(post);
        return post.getId();
    }

    @Override
    public int endorsePost(String handle, int id)
            throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        int endorsedUserId = -1;
        Post endorsedPost = null;
        for (Account account : allAccounts) {
            if (account.getHandle() == handle) {
                endorsedUserId = account.getId();
            }

        }
        for (Post post : allPosts) {
            if (post.getId() == id) {

                endorsedPost = post;
            }
        }
        if (endorsedUserId == -1) {
            throw new HandleNotRecognisedException("Handle was not recognised.");
        }
        if (endorsedPost == null) {
            throw new PostIDNotRecognisedException("Post ID was not found nor recognised");
        }
        if (endorsedPost instanceof Endorsement) {
            throw new NotActionablePostException("The post cannot be endorsed because it is an endorsement."); // Endorsements
                                                                                                               // are
                                                                                                               // not
                                                                                                               // transitive?????
        }
        // Creates the endorsement and adds it to the allPosts array also increments
        // nextPostId
        Endorsement endorsementPost = new Endorsement(handle, endorsedPost.getMessage(), endorsedPost.getHandle(),
                endorsedPost.getId(), nextPostId);
        nextPostId++;
        allPosts.add(endorsementPost);

        return endorsementPost.getId();
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        // TODO Auto-generated method stub
        int user = -1;
        // Looks for the account with the passed in handle and sets user to that users
        // id
        for (Account account : allAccounts) {
            if (account.getHandle() == handle) {
                user = account.getId();
            }
        }
        if (user == -1) {
            throw new HandleNotRecognisedException("Invalid Handle Provided");
        }
        Post thePost = null;
        for (Post post : allPosts) {
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
        // Creates a comment and adds it to the allPosts array also increments
        // nextPostId
        Comment comment = new Comment(handle, message, id, nextPostId);
        nextPostId++;
        allPosts.add(comment);
        return comment.getId();
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        Post emptyPost = null;
        // Looks for empty post
        for (Post post : allPosts) {
            if (post.getHandle() == null) {
                emptyPost = post;
            }
        }
        // if emptyPost isnt found it hasnt been added to allPosts yet so add it and set
        // emptyPost
        if (emptyPost == null) {
            allPosts.add(Post.emptyPost);
            emptyPost = Post.emptyPost;
        }
        Post toRemove = null;
        for (Post post : allPosts) {
            // Checks if the post is the empty post
            System.out.println("Post: " + post.getHandle());
            if (post.getHandle() != null) {

                // Checks if the post is an endorsement if it is checks if the endorsements
                // parent is the post to delete
                // then deletes it
                if (post instanceof Endorsement) {
                    Endorsement temp = (Endorsement) post;
                    if (temp.getOgId() == id) {
                        deletePost(temp.getId());
                    }
                }
                // Sets the parent of the comment to the empty post.
                if (post instanceof Comment) {
                    Comment temp = (Comment) post;
                    temp.setParentId(emptyPost.getId());

                }
                // Post to delete found
                if (post.getId() == id) {
                    toRemove = post;

                }
            }
        }

        if (toRemove == null) {
            throw new PostIDNotRecognisedException("Post To Be Deleted Not Found");
        }
        // delete post by removing reference to it
        allPosts.remove(toRemove);

    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        // TODO Auto-generated method stub
        int endorsements = 0;
        int comments = 0;
        Post thePost = null;
        for (Post post : allPosts) {
            if (post.getId() == id) {
                thePost = post;
            }
            // Checks if post is an endorsement and checks if its parent is id
            if (post instanceof Endorsement) {
                Endorsement temp = (Endorsement) post;
                if (temp.getOgId() == id) {
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
        // Creates the output in specified structure
        String output = "ID: " + Integer.toString(thePost.getId()) + "\nAccount: " + thePost.getHandle()
                + "\nNo. endorsements: " + Integer.toString(endorsements) + " | No. comments: "
                + Integer.toString(comments) + "\n" + thePost.getMessage();
        return output;
    }

    @Override
    public StringBuilder showPostChildrenDetails(int id)
            throws PostIDNotRecognisedException, NotActionablePostException {
        // TODO Auto-generated method stub
        // A Recursive algorithm to generate the postChildrenDetails structure

        StringBuilder stringBuilder = new StringBuilder();
        // Adds individual post string to the string builder
        stringBuilder.append(showIndividualPost(id));
        boolean commentAdded = false;
        for (Post post : allPosts) {
            // An endorsement post id was provided
            if (post.getId() == id) {
                if (post instanceof Endorsement) {
                    throw new NotActionablePostException("An endorsement post was provided");
                }
            }
            // checks if post is a comment
            if (post instanceof Comment) {
                Comment comment = (Comment) post;
                // Checks if the comments parent is this post
                if (comment.getParentId() == id) {
                    // Used to make sure the output is structured correctly ie looks right
                    if (!commentAdded) {
                        stringBuilder.append("\n|");
                    }
                    commentAdded = true;
                    // calls this method again with the comment being the post and finding children
                    // of this comment
                    StringBuilder result = showPostChildrenDetails(post.getId());
                    // adds this string to start of result so output is structured correctly
                    result.insert(0, "\n| > ");
                    // Adds the relevant spaces so its indented correctly so has a tree like
                    // structure
                    for (int i = 1; i < result.length(); i++) {
                        if (result.charAt(i) == '\n') {
                            // +1 for the spaces to be added after \n not before
                            result.insert(i + 1, "    ");
                        }
                    }
                    // Adds the result to the string builder
                    stringBuilder.append(result);
                }
            }
        }
        // So output looks correct ie new lines where there should be
        if (!commentAdded) {
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    @Override
    public int getNumberOfAccounts() {
        return allAccounts.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        // TODO Auto-generated method stub
        int postCount = 0;
        // Counts all posts which are original post ie not endorsement or comment
        for (Post post : allPosts) {
            if (post instanceof Endorsement) {
                continue;
            }
            if (post instanceof Comment) {
                continue;
            }
            if (post.getHandle() == null) {
                continue;
            }
            postCount++;

        }
        return postCount;
    }

    @Override
    public int getTotalEndorsmentPosts() { // testing needed

        int endorsementCount = 0;
        for (Post post : allPosts) {
            if (post instanceof Endorsement) {

                endorsementCount++;
            }
        }
        return endorsementCount;
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        int commentCount = 0;
        // Counts all the comment posts
        for (Post post : allPosts) {
            if (post instanceof Comment) {
                commentCount++;
            }
        }
        return commentCount;
    }

    @Override
    public int getMostEndorsedPost() { // testing needed

        int mostEndorsedPostId = -1;
        int maxNumberOfEndorsements = 0;
        for (Post post : allPosts) { // This will go through endorsements as well however since we don't allow
                                     // endorsement posts to be endorsed, it should return 0.
            if (getNumberOfEndorsements(post.getId()) > maxNumberOfEndorsements) {
                maxNumberOfEndorsements = getNumberOfEndorsements(post.getId());

                mostEndorsedPostId = post.getId();
            }
        }
        return mostEndorsedPostId;
    }

    @Override
    public int getMostEndorsedAccount() { // TESTING DEFINITELY NEEDED
        // TODO Auto-generated method stub
        int maxEndorsements = 0;
        int maxEndorsementsId = -1;

        for (Account account : allAccounts) {
            // int maxEndorsements = 0;

            int endorsementCount = 0;
            for (Post post : allPosts) {
                if (post.getHandle() == account.getHandle()) {
                    // There's probably a better way of doing this.
                    endorsementCount += getNumberOfEndorsements(post.getId());

                }

            }
            if (endorsementCount >= maxEndorsements) {
                maxEndorsementsId = account.getId();
                maxEndorsements = endorsementCount;
            }
        }
        return maxEndorsementsId;
    }

    @Override
    public void erasePlatform() {
        // TODO Auto-generated method stub
        this.allAccounts = new ArrayList<Account>();
        this.allPosts = new ArrayList<Post>();
        this.nextPostId = 1;
        this.nextAccountId = 0;

    }

    @Override
    public void savePlatform(String filename) throws IOException { // Should the file name include the .ser extension
        // TODO Auto-generated method stub
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./" + filename));
        out.writeObject(this);
        out.close();

    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./" + filename));
        SocialMedia loadedSocialMedia = (SocialMedia) in.readObject();
        this.allAccounts = loadedSocialMedia.allAccounts;
        this.allPosts = loadedSocialMedia.allPosts;

    }

    public int getNumberOfEndorsements(int id) {
        int endorsementCount = 0;
        for (Post post : allPosts) {
            if (post instanceof Endorsement) {
                Endorsement endorsementPost = (Endorsement) post;
                if (endorsementPost.getOgId() == id) {
                    endorsementCount++;
                }

            }
        }
        return endorsementCount;
    }

}
