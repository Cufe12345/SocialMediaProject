package socialmedia;

import java.util.ArrayList;
// Imported array list package. Check if it's allowed.

import java.util.ArrayList;
import java.io.Serializable;

public class Account implements Serializable {
    private String handle;
    private String description;
    private int accountId;
    
    

    public Account(String handle,int nextAccountId) {
        this.handle = handle;
        this.description = "";
        this.accountId = nextAccountId;
    }

    public Account(String handle, String description,int nextAccountId) {
        this.handle = handle;
        this.description = description;
        this.accountId = nextAccountId;
    }

    public String getHandle() {
        return this.handle;
    }

    public int getId() {
        return this.accountId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHandle(String newHandle) {
        this.handle = newHandle;
    }
    
}
