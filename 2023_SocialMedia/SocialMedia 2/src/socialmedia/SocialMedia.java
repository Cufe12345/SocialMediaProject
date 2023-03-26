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
            if (handle.equals(a.getHandle())) // If there exists and account with the same handle, it throws the illegal handle exception.
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")) { // Checks that the handle is valid for all conditions.
                                                                                     
            throw new InvalidHandleException("This handle is invalid"); 
        }
        Account newAccount = new Account(handle, nextAccountId);
        nextAccountId++;
        allAccounts.add(newAccount);
        return newAccount.getId();

    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
        for (Account a : allAccounts) {
            if (handle.equals(a.getHandle())) // If there exists and account with the same handle, it throws the illegal handle exception.
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")) { // Checks that the handle is valid for all conditions.
                                                                                    
            throw new InvalidHandleException("This handle is invalid"); 
                                                                        
        }
        Account newAccount = new Account(handle, description,nextAccountId); // Creates a new account with the corresponding description
        nextAccountId++;
        allAccounts.add(newAccount);
        return newAccount.getId();
    }

    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
        for (Account a : allAccounts) {
            if (a.getId() == id) { // Goes through all the accounts and removes the account with its corresponding id.
                String handle = a.getHandle();
                allAccounts.remove(a); // 
                for (Post p : allPosts){
                    if (handle.equals(p.getHandle())){ // Remove all posts associated with that account.
                        // Since we already know these posts exist, the exception will not be thrown.
                        try{deletePost(p.getId());}
                        catch (PostIDNotRecognisedException e){}
                    }
                }
                return;

            }
        }
        // If not returned, then the account will not have been found so the exception needs to be thrown.
        throw new AccountIDNotRecognisedException("This ID was not recognised.");
    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {

        for (Account a : allAccounts) { 
            if (handle.equals(a.getHandle())) { // Goes through all the accounts and removes the account with its corresponding id.
                allAccounts.remove(a);
                for (Post p : allPosts){
                    if (handle.equals(p.getHandle())){ // Remove all posts associated with that account.
                        // Since we already know these posts exist, the exception will not be thrown.
                        try{deletePost(p.getId());} 
                        catch (PostIDNotRecognisedException e){}
                    }
                }
                return; 

            }
        }
        // If not returned, then the account will not have been found so the exception needs to be thrown.
        throw new HandleNotRecognisedException("The handle was not recognised.");

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

        Account tempAccount = null;
        for (Account a : allAccounts) {
            if (newHandle.equals(a.getHandle())) { // Checks if the new handle already exists so that it won't share a handle with another account
                throw new IllegalHandleException("Handle already taken");
            }
            if (oldHandle.equals(a.getHandle())) { // Finds the account which has the old handle so that it can be changed.
                tempAccount = a;
            }
        }
        if (newHandle.length() == 0 || newHandle.length() > 100 || newHandle.contains(" ")) { // Checks that the new handle is valid for all conditions.
                                                                                              
            throw new InvalidHandleException("This handle is invalid"); 
                                    
        }
        if (tempAccount == null) { // If no account had been found, then the tempAccount variable will still be null.
            throw new HandleNotRecognisedException("Account handle not recognised");
        } else {
            tempAccount.setHandle(newHandle);
        }
        for (Post p: allPosts){ // Finds all posts with the old handle and replaces the associated handle with the new one.
            if (oldHandle.equals(p.getHandle())){
                p.setHandle(newHandle);
            }
        }

    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        for (Account a : allAccounts) {

            if (handle.equals(a.getHandle())) { // Finds the account with the handle and sets the new handle.
                a.setDescription(description); 
                return; 
            }
        }
        // If not returned, then no account with that handle has been found so the exception will be thrown
        throw new HandleNotRecognisedException("The handle was not recognised.");
    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {
        String message = "";
        int endorsementCount = 0;
        int postCount = 0;
        for (Post post : allPosts) { // Counts the endorsements, posts and comments so that it can be outputted later.
            if (handle.equals(post.getHandle())) {
                if (post instanceof Endorsement) {
                    endorsementCount++;
                }
                postCount++;
            }
        }
        for (Account a : allAccounts) {
            if (handle.equals(a.getHandle())) {
                message = String.format(
                        "ID: %1$s \n Handle: %2$s \n Description: %3$s \n Post count: %4$s \n Endorse count: %5$s",
                        a.getId(),
                        a.getHandle(), a.getDescription(), Integer.toString(postCount),
                        Integer.toString(endorsementCount));
                                                                                             
                return message;
            }
        }
        // If message was not returned, then no account with that handle has been found so the exception will be thrown
        throw new HandleNotRecognisedException("This handle was not recognised.");

    }

    @Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        int user = -1;
        // Looks for the account with the passed in handle and sets user to that users
        // id
        for (Account account : allAccounts) {
            if (handle.equals(account.getHandle())) {
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
            if (handle.equals(account.getHandle())) {
                endorsedUserId = account.getId();
            }


        }   
        for (Post post: allPosts){ // Checks all the posts and finds the post with the corrsponding id and set the post for endorsement.
            if (post.getId() == id){



                endorsedPost = post;
            }
        }
        if (endorsedUserId == -1) { // If no account was found the id will not change from -1 so the handle was not recognised.
            throw new HandleNotRecognisedException("Handle was not recognised.");
        }
        if (endorsedPost == null) { // If the endorsed post was not found, the exception should be thrown
            throw new PostIDNotRecognisedException("Post ID was not found nor recognised");
        }
        if (endorsedPost instanceof Endorsement) { // Users cannot endorse an endorsement.
            throw new NotActionablePostException("The post cannot be endorsed because it is an endorsement.");
                                                                                                              

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
            if (handle.equals(account.getHandle())) {
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
            //System.out.println("Post: " + post.getHandle());
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

            if(post.getHandle() == null){

                continue;
            }
            postCount++;

        }
        return postCount;
    }

    @Override

    public int getTotalEndorsmentPosts() { 
        int endorsementCount=0;
        for (Post post : allPosts){
            if(post instanceof Endorsement){ // If the Post is also an instance of the child class endorsement, it will increment the number of endorsements


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
    public int getMostEndorsedPost() {


        int mostEndorsedPostId=-1;
        int maxNumberOfEndorsements=0;
        for (Post post : allPosts){ // This will go through endorsements as well however since endorsement posts cannot be endorsed, it should return 0.
            if (getNumberOfEndorsements(post.getId()) > maxNumberOfEndorsements){ // If the next post has more endorsements, than the max, it should replace the max

                maxNumberOfEndorsements = getNumberOfEndorsements(post.getId());

                mostEndorsedPostId = post.getId();
            }
        }
        return mostEndorsedPostId;
    }

    @Override
    public int getMostEndorsedAccount() { 
        // TODO Auto-generated method stub
        int maxEndorsements = 0;
        int maxEndorsementsId = -1;


        for (Account account : allAccounts){            
            int endorsementCount = 0;
            for (Post post : allPosts){ // Goes through the posts of every account, counting the endorsements for each post.

                if (post.getHandle() == account.getHandle()) {
                    // There's probably a better way of doing this.
                    endorsementCount += getNumberOfEndorsements(post.getId());

                }

            }
            if (endorsementCount >= maxEndorsements) { // If the endorsement count for an account is greater than the max, then that account shall be the new max
                maxEndorsementsId = account.getId();
                maxEndorsements = endorsementCount;
            }
        }
        return maxEndorsementsId;
    }

    @Override
    public void erasePlatform() {
        // TODO Auto-generated method stub

        this.allAccounts = new ArrayList<Account>(); // Resets all of the variables to what they were orginally
        this.allPosts = new ArrayList<Post>(); // Any existing data will have been overrided.
        this.nextPostId =1;
        this.nextAccountId=0;


    }

    @Override
    public void savePlatform(String filename) throws IOException { // WHERE SHOULD IT BE SAVED RELATIVE TO THE SCRIPTS
        // TODO Auto-generated method stub

       ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./"+filename)); // Saves the platform as an object to a ser file.
       out.writeObject(this);
       out.close();
    
       


    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./"+filename)); // Reads from the file and loads in the platform as an object

        SocialMedia loadedSocialMedia = (SocialMedia) in.readObject();
        
        // Sets all the attributes to the variables saved in the file so that it can be fully loaded in.
        this.allAccounts = loadedSocialMedia.allAccounts;
        this.allPosts = loadedSocialMedia.allPosts;

        this.nextAccountId = loadedSocialMedia.nextAccountId;
        this.nextPostId = loadedSocialMedia.nextPostId;
    }
    public int getNumberOfEndorsements(int id){
        int endorsementCount=0;
        for (Post post : allPosts){
            if (post instanceof Endorsement){ // When the post is an endorsement of the post with the given id, the count will be incremented.

                Endorsement endorsementPost = (Endorsement) post;
                if (endorsementPost.getOgId() == id) {
                    endorsementCount++;
                }

            }
        }
        return endorsementCount;
    }

}
