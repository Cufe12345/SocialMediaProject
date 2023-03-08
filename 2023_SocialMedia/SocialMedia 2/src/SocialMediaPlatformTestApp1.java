import java.text.NumberFormat.Style;

import javax.naming.InvalidNameException;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
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
			id1 = platform.createAccount("my_handle1", "hello, this is a new awesome account");
			id2 = platform.createAccount("my_handle2", "hello, this is a new awesome account");

			platform.createPost("my_handle", "This is a post");
			platform.createPost("my_handle1", "This is a post");
			platform.createPost("my_handle1", "This is a post");


			platform.endorsePost("my_handle2", 0);
			platform.endorsePost("my_handle2", 0);
			platform.endorsePost("my_handle1", 0);
			platform.endorsePost("my_handle", 1);
			platform.endorsePost("my_handle", 1);
			platform.endorsePost("my_handle", 2);
			platform.endorsePost("my_handle", 2);


			System.out.println(platform.getTotalEndorsmentPosts());
			System.out.println(platform.getMostEndorsedAccount());
			System.out.println(platform.getMostEndorsedPost());

		} catch (IllegalHandleException e) {
			System.out.println("Thrown");
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			System.out.println("Thrown yeayy");
			assert (false) : "InvalidHandleException thrown incorrectly";
		} /*catch (AccountIDNotRecognisedException e) {
			System.out.println("hello");
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		} */catch (HandleNotRecognisedException e){
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		}  catch (InvalidPostException e){
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (NotActionablePostException e){
			System.out.println("Non actionable bddddadadad");
			assert (false) : "NotActionablePostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e){
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		}

	}

}
