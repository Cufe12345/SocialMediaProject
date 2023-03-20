package socialmedia;

public class Endorsement extends Post {
    private int ogPostId;
    

    public Endorsement(String handle, String message, String ogHandle, int ogId,int nextPostId) {
        super(handle, String.format("EP@%1$s: %2$s", ogHandle, message),nextPostId);
        this.ogPostId = ogId;
        
        
        // Will it still create a new id?
    }
    public int getOgId(){
        return ogPostId;
    }
}