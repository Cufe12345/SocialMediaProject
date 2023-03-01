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
		// TODO Auto-generated method stub
		return 0;
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

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
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
		return 0;
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
