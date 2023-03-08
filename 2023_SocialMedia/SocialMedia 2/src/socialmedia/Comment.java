package socialmedia;

public class Comment extends Post {
    private int parentId;


    public Comment(String handle, String message, int parentId) {
        super(handle, message);
        this.parentId = parentId;
    }

    public int getParentId() {
        // TODO Auto-generated method stub
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
