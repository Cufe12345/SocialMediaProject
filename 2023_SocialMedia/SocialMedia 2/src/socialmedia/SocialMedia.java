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
		for (Account a : Account.allAccounts){
            if (a.getHandle() == handle) // Lowercase maybe
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")){ // The system limit of characters may not be 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each condition.
        }
        Account newAccount = new Account(handle);
        return newAccount.getId();
        
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		for (Account a : Account.allAccounts){
            if (a.getHandle() == handle) // Lowercase maybe
            {
                throw new IllegalHandleException("This handle is already being used by another account.");
            }
        }
        if (handle.length() == 0 || handle.length() > 100 || handle.contains(" ")){ // The system limit of characters may not be 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each condition.
        }
        Account newAccount = new Account(handle, description);
        return newAccount.getId();
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for (Account a: Account.allAccounts){
            if (a.getId() == id)
            {
                Account.allAccounts.remove(a); // This may not work?
				return;
            }
        }
		throw new AccountIDNotRecognisedException("This ID was not recognised.");
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for (Account a: Account.allAccounts){
            if (a.getHandle() == handle)
            {
                Account.allAccounts.remove(a); // This may not work?
                return; //  This might be a bad way of doing it, otherwise have a boolean variable
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");


	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account tempAccount = null;
		for (Account a: Account.allAccounts){
			if (a.getHandle() == newHandle)
			{
				throw new IllegalHandleException("Handle already taken");
			}
			if (a.getHandle() == oldHandle)
			{
				tempAccount = a;
			}
		}
		if (newHandle.length() == 0 || newHandle.length() > 100 || newHandle.contains(" ")){ // The system limit of characters may not be 100 characters.
            throw new InvalidHandleException("This handle is invalid"); // Maybe have a different message for each condition.
        }
		if (tempAccount == null){
			throw new HandleNotRecognisedException("Account handle not recognised");
		}
		else{
			tempAccount.setHandle(newHandle);
		}

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        for (Account a: Account.allAccounts){
            if (a.getHandle() == handle)
            {
                a.setDescription(description); // This may not work?
                return; //  This might be a bad way of doing it, otherwise have a boolean variable
            }
        }
        throw new HandleNotRecognisedException("The handle was not recognised.");
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
        String message = "";
        for (Account a: Account.allAccounts)
        {
            if (a.getHandle() == handle)
            {
                message = String.format("ID: %1$s \n Handle: %2$s \n Description: %3$s \n Post count: \n Endorse count: ", a.getId(),a.getHandle(), a.getDescription()); // Do post/endorsement count later
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
        int endorsedUserId = -1;
        Post endorsedPost = null;
        for (Account account : Account.allAccounts) {
            if (account.getHandle() == handle) {
                endorsedUserId = account.getId();
            }
        }   
        for (Post post: Post.allPosts){
            if (post.getId() == id){
                endorsedPost = post;
            }
        }
        if (endorsedUserId == -1){
            throw new HandleNotRecognisedException("Handle was not recognised.");
        }
        if (endorsedPost == null){
            throw new PostIDNotRecognisedException("Post ID was not found nor recognised");
        }
        if (endorsedPost instanceof Endorsement){
            throw new NotActionablePostException("The post cannot be endorsed because it is an endorsement."); // Endorsements are not transitive?????
        }
        Endorsement endorsementPost = new Endorsement(handle, endorsedPost.getMessage(), endorsedPost.getHandle(), endorsedPost.getId());
        
        return endorsementPost.getId();
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
                    if (temp.getOgId()== id) {
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
                if (temp.getOgId() == id) {
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
    public int getTotalEndorsmentPosts() { // testing needed
        int endorsementCount=0;
        for (Post post : Post.allPosts){
            if(post instanceof Endorsement){
                endorsementCount++;
            }
        }
        return endorsementCount;
    }

    @Override
    public int getTotalCommentPosts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMostEndorsedPost() { // testing needed
        int mostEndorsedPostId=-1;
        int maxNumberOfEndorsements=0;
        for (Post post : Post.allPosts){ // This will go through endorsements as well however since we don't allow endorsement posts to be endorsed, it should return 0.
            if (post.getNumberOfEndorsements() > maxNumberOfEndorsements){
                maxNumberOfEndorsements = post.getNumberOfEndorsements();
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
        for (Account account : Account.allAccounts){
            //int maxEndorsements = 0;
            
            int endorsementCount = 0;
            for (Post post : Post.allPosts){
                if (post.getHandle() == account.getHandle()) {
                   // There's probably a better way of doing this.
                    endorsementCount += post.getNumberOfEndorsements();
                  
                }
                
            }
            if (endorsementCount >= maxEndorsements){
                maxEndorsementsId = account.getId();
                maxEndorsements = endorsementCount;
            }
        }
        return maxEndorsementsId;
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
