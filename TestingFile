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
import java.io.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks\n and run it on your SocialMedia class (not the
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
		while (true) {
			try {
				Console cnsl = System.console();
				if (cnsl == null) {
					System.out.println(
							"No console available");
					return;
				}
				String str = cnsl.readLine(
				// "A for add account \n P:Post\n C: comment\n E: endorsement\n D: Delete Post\n
				// DA : Delete Account"
				// +
				// " U: Update Account Descrip\n gNA: get number accounts\n TOP: total original
				// posts\n TEP: Total endorsement posts"
				// +
				// "TCP: Total comment posts\n SA: Show Account\n SIP: Show indivdual post\n
				// SPCD: Show post children details"
				// +
				// "GMEP: Get most endorsed post\n GMEA: Get most endorsed account\n EP: Erase
				// platform\n SP: Save Platform"
				// +
				// "LP: Load Platform"
				);
				switch (str) {
					case "A":
						String handle = cnsl.readLine("Enter handle");
						String description = cnsl.readLine("Enter description");
						if (description.equals("")) {
							platform.createAccount(handle);
						} else {
							platform.createAccount(handle, description);
						}
						break;
					case "P":
						String handle1 = cnsl.readLine("Enter handle");
						String post = cnsl.readLine("Enter post");
						System.out.println(platform.createPost(handle1, post));
						break;
					case "C":
						String handle2 = cnsl.readLine("Enter handle");
						String post2 = cnsl.readLine("Enter post");
						int postID = Integer.parseInt(post2);
						String comment = cnsl.readLine("Enter comment");
						System.out.println(platform.commentPost(handle2, postID, comment));
						break;
					case "E":
						String handle3 = cnsl.readLine("Enter handle");
						String post3 = cnsl.readLine("Enter post");
						int postID2 = Integer.parseInt(post3);
						System.out.println(platform.endorsePost(handle3, postID2));
						break;
					case "D":
						String post4 = cnsl.readLine("Enter post");
						int postID3 = Integer.parseInt(post4);
						platform.deletePost(postID3);
						break;
					case "DA":
						String option = cnsl.readLine("Handle or ID? (H/I)");
						if (option.equals("H")) {
							String handle4 = cnsl.readLine("Enter handle");
							platform.removeAccount(handle4);
						} else if (option.equals("I")) {
							String id1 = cnsl.readLine("Enter ID");
							int id2 = Integer.parseInt(id1);
							platform.removeAccount(id2);
						} else {
							System.out.println("Invalid option");
						}
						break;
					case "U":
						String handle5 = cnsl.readLine("Enter handle");
						description = cnsl.readLine("Enter description");
						platform.updateAccountDescription(handle5, description);
						break;
					case "gNA":
						System.out.println(platform.getNumberOfAccounts());
						break;
					case "TOP":
						System.out.println(platform.getTotalOriginalPosts());
						break;
					case "TEP":
						System.out.println(platform.getTotalEndorsmentPosts());
						break;
					case "TCP":
						System.out.println(platform.getTotalCommentPosts());
						break;
					case "SA":
						String handle6 = cnsl.readLine("Enter handle");
						System.out.println(platform.showAccount(handle6));
						break;
					case "SIP":
						String post5 = cnsl.readLine("Enter post");
						int postID4 = Integer.parseInt(post5);
						System.out.println(platform.showIndividualPost(postID4));
						break;
					case "SPCD":
						String post6 = cnsl.readLine("Enter post");
						int postID5 = Integer.parseInt(post6);
						System.out.println(platform.showPostChildrenDetails(postID5));
						break;
					case "GMEP":
						System.out.println(platform.getMostEndorsedPost());
						break;
					case "GMEA":
						System.out.println(platform.getMostEndorsedAccount());
						break;
					case "EP":
						platform.erasePlatform();
						break;
					case "SP":
						String fileName = cnsl.readLine("Enter file name");
						platform.savePlatform(fileName);
						break;
					case "LP":
						String fileName2 = cnsl.readLine("Enter file name");
						platform.loadPlatform(fileName2);
						break;
					default:
						System.out.println("Invalid input");
						break;
				}

				// id = platform.createAccount("user1");

				// Integer id2 = platform.createAccount("user2");
				// Integer id3 = platform.createAccount("user3");
				// Integer id4 = platform.createAccount("user4");
				// Integer id5 = platform.createAccount("user5");
				// // Create comments
				// int result = platform.createPost("user1"\n "Pepperoni pizza is best");
				// int result2 = platform.commentPost("user2"\n result\n "Nah margaritia pizza
				// is best");
				// int result3 = platform.commentPost("user1"\n result2\n "u dumb");
				// int result4 = platform.commentPost("user2"\n result3\n "no u");
				// int result5 = platform.commentPost("user3"\n result\n "Facts");
				// int result6 = platform.commentPost("user5"\n result\n "Poor");
				// int result7 = platform.commentPost("user1"\n result6\n "u wrong");
				// // Create endorsements
				// int endorse1 = platform.endorsePost("user2"\n result);
				// int endorse2 = platform.endorsePost("user3"\n result);
				// int endorse3 = platform.endorsePost("user2"\n result5);
				// int endorse4 = platform.endorsePost("user3"\n result5);
				// int endorse5 = platform.endorsePost("user4"\n result5);
				// int endorse6 = platform.endorsePost("user5"\n result5);

				// System.out.println(platform.showPostChildrenDetails(result));

				assert (platform.getNumberOfAccounts() == 1)
						: "number of accounts registered in the system does not match";

				// platform.removeAccount(id);
				assert (platform.getNumberOfAccounts() == 0)
						: "number of accounts registered in the system does not match";

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
			} catch (IOException e) {
				assert (false) : "isijdoiso";
			} catch (Exception e) {
				assert (false) : "sjjudj";
			}

		}

	}

}
