import socialmedia.AccountIDNotRecognisedException;
import socialmedia.BadSocialMedia;
import socialmedia.HandleNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMediaPlatform;

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
public class SocialMediaPlatformTestApp {

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
		try {
			id = platform.createAccount("user1");
			Integer id2 = platform.createAccount("user2");
			Integer id3 = platform.createAccount("user3");
			Integer id4 = platform.createAccount("user4");
			Integer id5 = platform.createAccount("user5");
			// Create comments
			int result = platform.createPost("user1", "Pepperoni pizza is best");
			int result2 = platform.commentPost("user2", result, "Nah margaritia pizza is best");
			int result3 = platform.commentPost("user1", result2, "u dumb");
			int result4 = platform.commentPost("user2", result3, "no u");
			int result5 = platform.commentPost("user3", result, "Facts");
			int result6 = platform.commentPost("user5", result, "Poor");
			int result7 = platform.commentPost("user1", result6, "u wrong");
			// Create endorsements
			int endorse1 = platform.endorsePost("user2", result);
			int endorse2 = platform.endorsePost("user3", result);
			int endorse3 = platform.endorsePost("user2", result5);
			int endorse4 = platform.endorsePost("user3", result5);
			int endorse5 = platform.endorsePost("user4", result5);
			int endorse6 = platform.endorsePost("user5", result5);

			System.out.println(platform.showPostChildrenDetails(result));

			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "sjjudj";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "iuiisdi";
		} catch (PostIDNotRecognisedException e) {
			System.out.println("sfkjfndudfjdjifdio");
			assert (false) : "jnsikd";
		} catch (NotActionablePostException e) {
			assert (false) : "isijdoiso";
		}

	}

}
