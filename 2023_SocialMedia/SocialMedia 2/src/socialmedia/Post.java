package socialmedia;

import java.util.ArrayList;

public class Post {
    private int id;
    private String handle;
    private String message;
    private boolean emptyPost;
    public static ArrayList<Post> allPosts = new ArrayList<Post>();
    public static int nextId;


    public Post(String handle, String message) {
        id = nextId;
        nextId++;
        this.handle = handle;
        this.message = message;
        allPosts.add(this);
    }

    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getMessage() {
        return message;
    }

    public boolean getEmptyPost() {
        return emptyPost;
    }

    public void setEmptyPost(boolean value) {
        emptyPost = value;
    }
    public int getNumberOfEndorsements(){
        int endorsementCount=0;
        for (Post post : allPosts){
            if (post instanceof Endorsement){
                Endorsement endorsementPost = (Endorsement) post;
                if (endorsementPost.getOgId() == this.id){
                    endorsementCount++;
                }
                
            }
        }
        return endorsementCount;
    }

}
