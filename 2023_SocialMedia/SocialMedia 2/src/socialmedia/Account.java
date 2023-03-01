package socialmedia;
import java.util.ArrayList;
// Imported array list package. Check if it's allowed.

import java.util.ArrayList;

public class Account {
    private String handle;
    private String description;
    private int accountId;
    public static ArrayList<Account> allAccounts = new ArrayList<Account>();
    public static int nextAccountId=0;
    public Account(String handle) {
        this.handle = handle;
        this.description = "";
        this.accountId = nextAccountId++;
        allAccounts.add(this);
    }

    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.accountId = nextAccountId++;
        allAccounts.add(this);
    }
    public String getHandle(){
        return this.handle;
    }
    public int getId()
    {
        return this.accountId;
    }
    public String getDescription()
    {
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setHandle(String newHandle){
        this.handle = newHandle;
    }
}
