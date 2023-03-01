import java.text.NumberFormat.Style;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.SocialMediaPlatform;
import socialmedia.HandleNotRecognisedException;


/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp1 {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		Integer id1;
		Integer id2;
		try {
			id = platform.createAccount("my_handle", "hello, this is a new awesome account");
			//assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";
			id1 = platform.createAccount("my_handle1", "hello, this is a new awesome account, trust me");
			//System.out.println(platform.showAccount("my_handle"));
			platform.removeAccount("my_handle");
			System.out.println(platform.getNumberOfAccounts());
			id2 = platform.createAccount("my_handle3", "hello, this is a new awesome account, trust me");
			System.out.println(platform.getNumberOfAccounts());

			platform.removeAccount(id1);

			System.out.println(platform.getNumberOfAccounts());
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			System.out.println("Thrown");
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			System.out.println("Thrown yeayy");
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			System.out.println("hello");
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		} catch (HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} 

	}

}
