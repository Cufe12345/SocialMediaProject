package socialmedia;

import java.util.ArrayList;

public class Account {
    private String handle;
    private String description;
    private int id;
    public static ArrayList<Account> allAccounts = new ArrayList<Account>();

    public Account(String handle) {
        this.handle = handle;
        this.description = "";
        allAccounts.add(this);
    }

    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
    }

    public String getHandle() {
        return handle;
    }

    public int getId() {
        return id;
    }

}
