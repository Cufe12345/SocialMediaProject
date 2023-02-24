package socialmedia;

public class Account {
    private String handle;
    private String description;
    private int id;
    public static Account[] allAccounts;

    public Account(String handle)
    {
        this.handle = handle;
        this.description = "";
    }
    public Account(String handle, String description)
    {
        this.handle = handle;
        this.description = description;
    }

}
